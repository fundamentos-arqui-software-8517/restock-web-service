package com.restock.arm.interfaces.rest;

import com.restock.arm.application.internal.commandservices.CustomSupplyCommandService;
import com.restock.arm.application.internal.queryservices.CustomSupplyQueryService;
import com.restock.arm.domain.model.CustomSupply;
import com.restock.arm.interfaces.rest.resources.CreateCustomSupplyResource;
import com.restock.arm.interfaces.rest.resources.CustomSupplyResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/custom-supplies")
@Tag(name = "ARM - Custom Supplies", description = "Business-specific supply catalog management")
@CrossOrigin(origins = "*")
public class CustomSupplyController {

    private final CustomSupplyCommandService commandService;
    private final CustomSupplyQueryService queryService;

    public CustomSupplyController(CustomSupplyCommandService commandService, CustomSupplyQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all custom supplies")
    @GetMapping
    public ResponseEntity<List<CustomSupplyResource>> getAll(
        @RequestParam(required = false) String businessId) {
        List<CustomSupply> results = businessId != null
            ? queryService.findByBusinessId(businessId)
            : queryService.findAll();
        return ResponseEntity.ok(results.stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get custom supply by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomSupplyResource> getById(@PathVariable String id) {
        return queryService.findById(id)
            .map(cs -> ResponseEntity.ok(toResource(cs)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new custom supply")
    @PostMapping
    public ResponseEntity<CustomSupplyResource> create(@Valid @RequestBody CreateCustomSupplyResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update a custom supply")
    @PutMapping("/{id}")
    public ResponseEntity<CustomSupplyResource> update(@PathVariable String id,
                                                        @Valid @RequestBody CreateCustomSupplyResource resource) {
        return commandService.update(id, resource)
            .map(cs -> ResponseEntity.ok(toResource(cs)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a custom supply")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CustomSupplyResource toResource(CustomSupply cs) {
        return new CustomSupplyResource(cs.getId(), cs.getSupplyId(), cs.getBusinessId(),
            cs.getNameOverride(), cs.getSubtitle(), cs.getCategory(), cs.getUomLabel(),
            cs.isPerishable(), cs.getMinStock(), cs.getMaxStock());
    }
}
