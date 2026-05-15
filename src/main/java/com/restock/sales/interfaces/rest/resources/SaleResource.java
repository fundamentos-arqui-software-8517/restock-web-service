package com.restock.sales.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Sale transaction response")
public record SaleResource(
    String id,
    String businessId,
    String branchId,
    String soldAt,
    double totalAmount,
    List<SaleItemResource> items
) {}
