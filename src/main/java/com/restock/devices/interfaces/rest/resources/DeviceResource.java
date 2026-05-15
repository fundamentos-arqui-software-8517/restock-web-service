package com.restock.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "IoT device response")
public record DeviceResource(
    String id,
    String name,
    String deviceKey,
    String branchId,
    String supplyId,
    String status,
    String type,
    String mqttTopic
) {}
