package com.restock.communications.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notification response")
public record NotificationResource(
    String id,
    String businessId,
    String title,
    String message,
    String type,
    boolean read,
    String createdAt,
    String relatedEntityId
) {}
