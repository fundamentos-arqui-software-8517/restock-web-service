package com.uitopic.restock.platform.devices.infrastructure.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uitopic.restock.platform.devices.domain.model.commands.UpdateDeviceMeasurementCommand;
import com.uitopic.restock.platform.devices.domain.model.valueobjects.MacAddress;
import com.uitopic.restock.platform.devices.domain.repositories.DeviceRepository;
import com.uitopic.restock.platform.devices.domain.services.DeviceCommandService;
import com.uitopic.restock.platform.shared.infrastructure.mqtt.publisher.MqttInventoryPublisher;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class MqttTelemetrySubscriber implements CommandLineRunner {

    private final IMqttClient mqttClient;
    private final DeviceRepository deviceRepository;
    private final DeviceCommandService deviceCommandService;
    private final MqttInventoryPublisher mqttInventoryPublisher;
    private final ObjectMapper objectMapper;

    @Value("${mqtt.topic.telemetry:restock/telemetry}")
    private String telemetryTopic;

    public MqttTelemetrySubscriber(
            IMqttClient mqttClient,
            DeviceRepository deviceRepository,
            DeviceCommandService deviceCommandService,
            MqttInventoryPublisher mqttInventoryPublisher,
            ObjectMapper objectMapper
    ) {
        this.mqttClient = mqttClient;
        this.deviceRepository = deviceRepository;
        this.deviceCommandService = deviceCommandService;
        this.mqttInventoryPublisher = mqttInventoryPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if (mqttClient == null) {
            log.warn("MQTT client is null or disabled. Telemetry subscriber will not start.");
            return;
        }

        try {
            log.info("Subscribing to MQTT telemetry topic: '{}'", telemetryTopic);
            mqttClient.subscribe(telemetryTopic, this::handleTelemetryMessage);
            log.info("Successfully subscribed to topic: '{}'", telemetryTopic);
        } catch (Exception e) {
            log.error("Failed to subscribe to MQTT telemetry topic '{}': {}", telemetryTopic, e.getMessage());
        }
    }

    private void handleTelemetryMessage(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        log.debug("Received telemetry message on topic {}: {}", topic, payload);

        try {
            TelemetryPayload telemetry = objectMapper.readValue(payload, TelemetryPayload.class);
            if (telemetry.macAddress() == null || telemetry.macAddress().isBlank()) {
                log.warn("Received telemetry message with missing MAC address: {}", payload);
                return;
            }

            MacAddress macAddress = new MacAddress(telemetry.macAddress());
            deviceRepository.findByMacAddress(macAddress).ifPresentOrElse(
                    device -> {
                        log.info("Processing telemetry for device ID '{}' (MAC: '{}')", device.getId(), telemetry.macAddress());
                        
                        double gross = telemetry.grossWeight() != null ? telemetry.grossWeight() : 0.0;
                        double tare = 0.0;
                        LocalDate calibrationDate = LocalDate.now();
                        String unitName = "grams";
                        String unitAbbr = "g";

                        if (device.getWeightMeasurement() != null) {
                            tare = device.getWeightMeasurement().tareWeight() != null ? device.getWeightMeasurement().tareWeight() : 0.0;
                            calibrationDate = device.getWeightMeasurement().calibrationDate() != null 
                                    ? device.getWeightMeasurement().calibrationDate() : LocalDate.now();
                            if (device.getWeightMeasurement().weightUnit() != null) {
                                unitName = device.getWeightMeasurement().weightUnit().unitName();
                                unitAbbr = device.getWeightMeasurement().weightUnit().abbreviation();
                            }
                        }

                        double net = Math.max(0.0, gross - tare);

                        UpdateDeviceMeasurementCommand command = new UpdateDeviceMeasurementCommand(
                                device.getId(),
                                net,
                                tare,
                                gross,
                                calibrationDate,
                                unitName,
                                unitAbbr
                        );

                        deviceCommandService.handle(command).ifPresentOrElse(
                                updatedDevice -> {
                                    log.info("Device measurements updated successfully via MQTT telemetria.");
                                    // Publish event to notify frontend (real-time updates)
                                    mqttInventoryPublisher.publishUpdate(updatedDevice);
                                },
                                () -> log.warn("Failed to update device measurements for device ID '{}'", device.getId())
                        );
                    },
                    () -> log.warn("Received telemetry for unregistered device MAC: '{}'", telemetry.macAddress())
            );

        } catch (Exception e) {
            log.error("Error processing telemetry message: {}", e.getMessage(), e);
        }
    }

    public record TelemetryPayload(String macAddress, Double grossWeight) {}
}
