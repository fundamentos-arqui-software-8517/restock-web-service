package com.restock.arm.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Custom supply response")
public record CustomSupplyResource(
    String id,
    String supplyId,
    String businessId,
    String nameOverride,
    String subtitle,
    String category,
    String uomLabel,
    boolean perishable,
    double minStock,
    double maxStock
) {}
