package com.restock.devices.infrastructure.persistence;

import com.restock.devices.domain.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface MongoDeviceRepository extends MongoRepository<Device, String> {
    List<Device> findByBranchId(String branchId);
    List<Device> findByStatus(String status);
    Optional<Device> findByDeviceKey(String deviceKey);
}
