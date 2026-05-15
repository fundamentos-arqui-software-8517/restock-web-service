package com.restock.devices.application.internal.commandservices;

import com.restock.devices.domain.model.Device;
import com.restock.devices.domain.repositories.DeviceRepository;
import com.restock.devices.interfaces.rest.resources.CreateDeviceResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceCommandService {

    private final DeviceRepository deviceRepository;

    public DeviceCommandService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device create(CreateDeviceResource resource) {
        Device device = Device.builder()
            .name(resource.name())
            .deviceKey(resource.deviceKey())
            .branchId(resource.branchId())
            .supplyId(resource.supplyId())
            .status(resource.status() != null ? resource.status() : "ACTIVE")
            .type(resource.type())
            .mqttTopic(resource.mqttTopic() != null ? resource.mqttTopic() : "restock/devices/" + resource.deviceKey() + "/readings")
            .build();
        return deviceRepository.save(device);
    }

    public Optional<Device> update(String id, CreateDeviceResource resource) {
        return deviceRepository.findById(id).map(existing -> {
            existing.setName(resource.name());
            existing.setDeviceKey(resource.deviceKey());
            existing.setBranchId(resource.branchId());
            existing.setSupplyId(resource.supplyId());
            existing.setStatus(resource.status());
            existing.setType(resource.type());
            existing.setMqttTopic(resource.mqttTopic());
            return deviceRepository.save(existing);
        });
    }

    public void delete(String id) {
        deviceRepository.deleteById(id);
    }
}
