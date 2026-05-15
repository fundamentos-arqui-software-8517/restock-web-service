package com.restock.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dashboard analytics summary")
public record DashboardResource(
    long totalBatches,
    long lowStockBatches,
    long nearExpiryBatches,
    long totalDevices,
    long activeDevices,
    long totalSales,
    double totalRevenue
) {}
