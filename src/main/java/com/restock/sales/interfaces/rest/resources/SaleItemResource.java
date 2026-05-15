package com.restock.sales.interfaces.rest.resources;

public record SaleItemResource(
    String supplyId,
    String supplyName,
    double quantity,
    double unitPrice,
    double subtotal
) {}
