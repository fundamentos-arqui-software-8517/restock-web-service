package com.restock.communications.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Create notification request")
public record CreateNotificationResource(
    @NotBlank String businessId,
    @NotBlank String title,
    @NotBlank String message,
    String type,
    String relatedEntityId
) {}
