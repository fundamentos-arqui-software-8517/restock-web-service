package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Root wrapper for batch inventory response — matches frontend contract")
public record BatchInventoryRootResource(
    BatchInventoryPayloadResource batchInventory
) {}
