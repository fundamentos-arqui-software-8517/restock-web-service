package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create or update batch request")
public record CreateBatchResource(
    @NotBlank String supplyId,
    @NotBlank String branchId,
    @PositiveOrZero double currentQuantity,
    String expirationDate,
    @PositiveOrZero double minStock,
    @PositiveOrZero double maxStock
) {}
