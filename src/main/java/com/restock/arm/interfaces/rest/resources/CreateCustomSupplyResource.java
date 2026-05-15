package com.restock.arm.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Create or update custom supply request")
public record CreateCustomSupplyResource(
    @NotBlank String supplyId,
    @NotBlank String businessId,
    String nameOverride,
    String subtitle,
    @NotBlank String category,
    @NotBlank String uomLabel,
    boolean perishable,
    @Positive double minStock,
    @Positive double maxStock
) {}
