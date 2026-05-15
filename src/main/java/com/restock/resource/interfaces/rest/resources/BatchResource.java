package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Batch record response")
public record BatchResource(
    String id,
    String supplyId,
    String branchId,
    double currentQuantity,
    String expirationDate,
    double minStock,
    double maxStock
) {}
