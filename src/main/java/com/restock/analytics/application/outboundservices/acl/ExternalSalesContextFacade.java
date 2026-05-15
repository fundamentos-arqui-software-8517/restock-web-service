package com.restock.analytics.application.outboundservices.acl;

/**
 * ACL outbound port — Analytics domain asks Sales domain for revenue metrics.
 * Implemented in sales/interfaces/acl/SalesContextFacade.
 */
public interface ExternalSalesContextFacade {
    long getTotalSalesCount();
    double getTotalRevenue();
}
