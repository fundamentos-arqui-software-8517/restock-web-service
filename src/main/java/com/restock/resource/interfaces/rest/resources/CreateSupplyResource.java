package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Create or update supply request")
public record CreateSupplyResource(
    @NotBlank String name,
    String description,
    @NotBlank String category,
    @NotBlank String uomLabel,
    boolean perishable
) {}
