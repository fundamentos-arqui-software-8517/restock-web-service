package com.restock.sales.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Register a sale transaction request")
public record CreateSaleResource(
    @NotBlank String businessId,
    @NotBlank String branchId,
    @NotEmpty List<SaleItemResource> items
) {}
