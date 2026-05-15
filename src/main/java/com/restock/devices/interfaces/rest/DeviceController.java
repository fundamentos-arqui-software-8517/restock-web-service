package com.restock.devices.interfaces.rest;

import com.restock.devices.application.internal.commandservices.DeviceCommandService;
import com.restock.devices.application.internal.queryservices.DeviceQueryService;
import com.restock.devices.domain.model.Device;
import com.restock.devices.interfaces.rest.resources.CreateDeviceResource;
import com.restock.devices.interfaces.rest.resources.DeviceResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@Tag(name = "Devices - IoT", description = "IoT device registration and management")
@CrossOrigin(origins = "*")
public class DeviceController {

    private final DeviceCommandService commandService;
    private final DeviceQueryService queryService;

    public DeviceController(DeviceCommandService commandService, DeviceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all devices")
    @GetMapping
    public ResponseEntity<List<DeviceResource>> getAll(@RequestParam(required = false) String branchId) {
        List<Device> results = branchId != null ? queryService.findByBranchId(branchId) : queryService.findAll();
        return ResponseEntity.ok(results.stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get device by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(d -> ResponseEntity.ok(toResource(d))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Register a new device")
    @PostMapping
    public ResponseEntity<DeviceResource> create(@Valid @RequestBody CreateDeviceResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update device info")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResource> update(@PathVariable String id, @Valid @RequestBody CreateDeviceResource resource) {
        return commandService.update(id, resource).map(d -> ResponseEntity.ok(toResource(d))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a device")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private DeviceResource toResource(Device d) {
        return new DeviceResource(d.getId(), d.getName(), d.getDeviceKey(), d.getBranchId(),
            d.getSupplyId(), d.getStatus(), d.getType(), d.getMqttTopic());
    }
}
