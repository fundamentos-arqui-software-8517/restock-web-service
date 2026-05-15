package com.restock.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response with token")
public record AuthenticatedUserResource(
    String id,
    String email,
    String role,
    String token
) {}
