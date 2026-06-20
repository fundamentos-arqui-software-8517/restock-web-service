package com.uitopic.restock.platform.shared.infrastructure.mqtt.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uitopic.restock.platform.devices.domain.model.aggregates.Device;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttInventoryPublisher {

    private final IMqttClient mqttClient;
    private final ObjectMapper objectMapper;

    @Value("${mqtt.topic.inventory-updates:restock/inventory/updates}")
    private String inventoryUpdatesTopic;

    public MqttInventoryPublisher(IMqttClient mqttClient, ObjectMapper objectMapper) {
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
    }

    public void publishUpdate(Device device) {
        if (mqttClient == null || !mqttClient.isConnected()) {
            log.warn("MQTT client is null or not connected. Cannot publish device update.");
            return;
        }

        try {
            DeviceUpdateMessage messagePayload = DeviceUpdateMessage.from(device);
            String jsonPayload = objectMapper.writeValueAsString(messagePayload);
            
            MqttMessage mqttMessage = new MqttMessage(jsonPayload.getBytes());
            mqttMessage.setQos(1); // At least once delivery
            
            log.info("Publishing device update to topic '{}' for device ID '{}'", inventoryUpdatesTopic, device.getId());
            mqttClient.publish(inventoryUpdatesTopic, mqttMessage);
        } catch (Exception e) {
            log.error("Failed to publish device update: {}", e.getMessage(), e);
        }
    }

    public record DeviceUpdateMessage(
        String deviceId,
        String macAddress,
        String status,
        String branchId,
        String assignedBatchId,
        Double netWeight,
        Double grossWeight,
        Double tareWeight,
        String weightUnitName,
        String weightUnitAbbr
    ) {
        public static DeviceUpdateMessage from(Device device) {
            Double net = null;
            Double gross = null;
            Double tare = null;
            String unitName = null;
            String unitAbbr = null;
            
            if (device.getWeightMeasurement() != null) {
                net = device.getWeightMeasurement().netWeight();
                gross = device.getWeightMeasurement().grossWeight();
                tare = device.getWeightMeasurement().tareWeight();
                if (device.getWeightMeasurement().weightUnit() != null) {
                    unitName = device.getWeightMeasurement().weightUnit().unitName();
                    unitAbbr = device.getWeightMeasurement().weightUnit().abbreviation();
                }
            }
            
            return new DeviceUpdateMessage(
                device.getId(),
                device.getMacAddress() != null ? device.getMacAddress().address() : null,
                device.getStatus() != null ? device.getStatus().name() : null,
                device.getBranchId(),
                device.getAssignedBatchId(),
                net,
                gross,
                tare,
                unitName,
                unitAbbr
            );
        }
    }
}
