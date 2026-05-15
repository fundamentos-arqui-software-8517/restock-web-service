package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supply catalog item response")
public record SupplyResource(
    String id,
    String name,
    String description,
    String category,
    String uomLabel,
    boolean perishable
) {}
