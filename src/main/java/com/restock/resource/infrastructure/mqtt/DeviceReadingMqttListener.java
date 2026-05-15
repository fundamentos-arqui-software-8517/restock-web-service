package com.restock.resource.infrastructure.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restock.resource.application.internal.commandservices.BatchCommandService;
import com.restock.resource.domain.repositories.BatchRepository;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Subscribes to MQTT device reading topics and updates batch quantities.
 * Topic pattern: restock/devices/{deviceId}/readings
 * Payload: { "deviceId": "...", "supplyId": "...", "quantity": 0.0 }
 */
@Component
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")
public class DeviceReadingMqttListener {

    private static final Logger log = LoggerFactory.getLogger(DeviceReadingMqttListener.class);

    private final MqttClient mqttClient;
    private final BatchCommandService batchCommandService;
    private final BatchRepository batchRepository;
    private final ObjectMapper objectMapper;

    @Value("${mqtt.topics.device-readings}")
    private String topicPattern;

    public DeviceReadingMqttListener(MqttClient mqttClient,
                                     BatchCommandService batchCommandService,
                                     BatchRepository batchRepository,
                                     ObjectMapper objectMapper) {
        this.mqttClient = mqttClient;
        this.batchCommandService = batchCommandService;
        this.batchRepository = batchRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void subscribe() {
        try {
            mqttClient.subscribe(topicPattern, 1, this::handleMessage);
            log.info("MQTT subscribed to topic: {}", topicPattern);
        } catch (Exception e) {
            log.error("Failed to subscribe to MQTT topic {}: {}", topicPattern, e.getMessage());
        }
    }

    private void handleMessage(String topic, MqttMessage message) {
        try {
            DeviceReadingPayload payload = objectMapper.readValue(message.getPayload(), DeviceReadingPayload.class);
            log.debug("MQTT reading from {}: supplyId={} quantity={}", topic, payload.supplyId(), payload.quantity());
            List<com.restock.resource.domain.model.Batch> batches = batchRepository.findBySupplyId(payload.supplyId());
            if (!batches.isEmpty()) {
                batchCommandService.updateQuantity(batches.get(0).getId(), payload.quantity());
                log.info("Updated batch {} quantity to {}", batches.get(0).getId(), payload.quantity());
            }
        } catch (Exception e) {
            log.error("Error processing MQTT message on topic {}: {}", topic, e.getMessage());
        }
    }

    record DeviceReadingPayload(String deviceId, String supplyId, double quantity, String timestamp) {}
}
