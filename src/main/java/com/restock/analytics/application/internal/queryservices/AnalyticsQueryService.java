package com.restock.analytics.application.internal.queryservices;

import com.restock.analytics.application.outboundservices.acl.ExternalDevicesContextFacade;
import com.restock.analytics.application.outboundservices.acl.ExternalInventoryContextFacade;
import com.restock.analytics.application.outboundservices.acl.ExternalSalesContextFacade;
import com.restock.analytics.interfaces.rest.resources.DashboardResource;
import org.springframework.stereotype.Service;

/**
 * Analytics query service — consumes only ACL ports, never other domains' internals.
 * Cross-domain access is mediated exclusively through ExternalXxxContextFacade interfaces.
 */
@Service
public class AnalyticsQueryService {

    private final ExternalInventoryContextFacade inventoryFacade;
    private final ExternalDevicesContextFacade devicesFacade;
    private final ExternalSalesContextFacade salesFacade;

    public AnalyticsQueryService(ExternalInventoryContextFacade inventoryFacade,
                                  ExternalDevicesContextFacade devicesFacade,
                                  ExternalSalesContextFacade salesFacade) {
        this.inventoryFacade = inventoryFacade;
        this.devicesFacade = devicesFacade;
        this.salesFacade = salesFacade;
    }

    public DashboardResource getDashboard() {
        return new DashboardResource(
            inventoryFacade.getTotalActiveBatches(),
            inventoryFacade.getLowStockBatchCount(),
            inventoryFacade.getNearExpiryBatchCount(30),
            devicesFacade.getTotalDevices(),
            devicesFacade.getActiveDeviceCount(),
            salesFacade.getTotalSalesCount(),
            salesFacade.getTotalRevenue()
        );
    }
}
