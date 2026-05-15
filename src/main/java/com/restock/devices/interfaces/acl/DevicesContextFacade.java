package com.restock.devices.interfaces.acl;

import com.restock.analytics.application.outboundservices.acl.ExternalDevicesContextFacade;
import com.restock.devices.domain.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

/**
 * ACL inbound adapter — Devices domain implements Analytics domain's outbound port.
 */
@Service
public class DevicesContextFacade implements ExternalDevicesContextFacade {

    private final DeviceRepository deviceRepository;

    public DevicesContextFacade(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public long getTotalDevices() {
        return deviceRepository.count();
    }

    @Override
    public long getActiveDeviceCount() {
        return deviceRepository.findByStatus("ACTIVE").size();
    }
}
