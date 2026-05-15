package com.restock.devices.infrastructure.persistence;

import com.restock.devices.domain.model.Device;
import com.restock.devices.domain.repositories.DeviceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final MongoDeviceRepository mongo;

    public DeviceRepositoryImpl(MongoDeviceRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Device> findAll() { return mongo.findAll(); }
    @Override public Optional<Device> findById(String id) { return mongo.findById(id); }
    @Override public List<Device> findByBranchId(String branchId) { return mongo.findByBranchId(branchId); }
    @Override public List<Device> findByStatus(String status) { return mongo.findByStatus(status); }
    @Override public Optional<Device> findByDeviceKey(String deviceKey) { return mongo.findByDeviceKey(deviceKey); }
    @Override public Device save(Device device) { return mongo.save(device); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
