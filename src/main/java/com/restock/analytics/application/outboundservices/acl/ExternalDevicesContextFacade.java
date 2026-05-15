package com.restock.analytics.application.outboundservices.acl;

/**
 * ACL outbound port — Analytics domain asks Devices domain for device metrics.
 * Implemented in devices/interfaces/acl/DevicesContextFacade.
 */
public interface ExternalDevicesContextFacade {
    long getTotalDevices();
    long getActiveDeviceCount();
}
