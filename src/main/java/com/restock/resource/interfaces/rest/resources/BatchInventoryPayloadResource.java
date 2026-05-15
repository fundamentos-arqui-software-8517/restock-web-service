package com.restock.resource.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Batch inventory analytics payload")
public record BatchInventoryPayloadResource(
    int totalActiveBatches,
    double totalActiveBatchesDeltaPercent,
    int nearExpiry30Days,
    List<BatchInventoryBatchResource> batches
) {}
