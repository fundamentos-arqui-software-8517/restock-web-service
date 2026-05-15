package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Single batch row in the inventory snapshot")
public record BatchInventoryBatchResource(
    String id,
    String supplyName,
    String subtitle,
    String category,
    String uomLabel,
    String expirationDate,
    double stock,
    boolean isPerishable,
    String perishableBadgeTone,
    String rowHighlight,
    double minStock,
    double maxStock
) {}
