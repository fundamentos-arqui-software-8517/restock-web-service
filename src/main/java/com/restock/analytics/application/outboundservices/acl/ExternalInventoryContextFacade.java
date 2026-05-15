package com.restock.analytics.application.outboundservices.acl;

/**
 * ACL outbound port — Analytics domain asks Resource domain for inventory metrics.
 * Implemented in resource/interfaces/acl/InventoryContextFacade.
 */
public interface ExternalInventoryContextFacade {
    long getTotalActiveBatches();
    long getLowStockBatchCount();
    long getNearExpiryBatchCount(int withinDays);
}
