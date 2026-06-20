package com.uitopic.restock.platform.shared.infrastructure.mqtt.configuration;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MqttConfig {

    @Value("${mqtt.enabled:true}")
    private boolean enabled;

    @Value("${mqtt.broker-url:tcp://localhost:1883}")
    private String brokerUrl;

    @Value("${mqtt.client-id:restock-web-service}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @Bean
    public IMqttClient mqttClient() {
        if (!enabled) {
            log.info("MQTT is disabled via configuration.");
            return null;
        }

        try {
            IMqttClient client = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            
            if (username != null && !username.isBlank()) {
                options.setUserName(username);
            }
            if (password != null && !password.isBlank()) {
                options.setPassword(password.toCharArray());
            }

            log.info("Connecting to MQTT Broker: {}", brokerUrl);
            client.connect(options);
            log.info("Connected to MQTT Broker successfully.");
            return client;
        } catch (MqttException e) {
            log.error("Failed to connect to MQTT Broker: {}. Error: {}", brokerUrl, e.getMessage());
            return null;
        }
    }
}
