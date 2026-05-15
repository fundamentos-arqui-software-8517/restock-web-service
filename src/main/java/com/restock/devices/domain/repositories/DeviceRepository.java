package com.restock.devices.domain.repositories;

import com.restock.devices.domain.model.Device;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for device persistence. */
public interface DeviceRepository {
    List<Device> findAll();
    Optional<Device> findById(String id);
    List<Device> findByBranchId(String branchId);
    List<Device> findByStatus(String status);
    Optional<Device> findByDeviceKey(String deviceKey);
    Device save(Device device);
    void deleteById(String id);
    long count();
}
