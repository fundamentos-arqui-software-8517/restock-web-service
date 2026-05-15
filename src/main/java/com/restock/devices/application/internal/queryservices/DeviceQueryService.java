package com.restock.devices.application.internal.queryservices;

import com.restock.devices.domain.model.Device;
import com.restock.devices.domain.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryService {

    private final DeviceRepository deviceRepository;

    public DeviceQueryService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> findAll() { return deviceRepository.findAll(); }
    public Optional<Device> findById(String id) { return deviceRepository.findById(id); }
    public List<Device> findByBranchId(String branchId) { return deviceRepository.findByBranchId(branchId); }
}
