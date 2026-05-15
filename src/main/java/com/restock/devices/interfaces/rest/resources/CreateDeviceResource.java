package com.restock.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Register or update IoT device request")
public record CreateDeviceResource(
    @NotBlank String name,
    @NotBlank String deviceKey,
    @NotBlank String branchId,
    String supplyId,
    String status,
    @NotBlank String type,
    String mqttTopic
) {}
