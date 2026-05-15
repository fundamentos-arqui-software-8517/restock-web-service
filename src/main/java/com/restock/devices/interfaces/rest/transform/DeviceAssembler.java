package com.restock.devices.interfaces.rest.transform;

import com.restock.devices.domain.model.Device;
import com.restock.devices.interfaces.rest.resources.CreateDeviceResource;
import com.restock.devices.interfaces.rest.resources.DeviceResource;
import org.springframework.stereotype.Component;

@Component
public class DeviceAssembler {

    public DeviceResource toResource(Device device) {
        return new DeviceResource(device.getId(), device.getName(), device.getDeviceKey(),
            device.getBranchId(), device.getSupplyId(), device.getStatus(),
            device.getType(), device.getMqttTopic());
    }

    public Device toEntity(CreateDeviceResource resource) {
        return Device.builder()
            .name(resource.name()).deviceKey(resource.deviceKey())
            .branchId(resource.branchId()).supplyId(resource.supplyId())
            .status(resource.status() != null ? resource.status() : "ACTIVE")
            .type(resource.type())
            .mqttTopic(resource.mqttTopic() != null ? resource.mqttTopic()
                : "restock/devices/" + resource.deviceKey() + "/readings")
            .build();
    }
}
