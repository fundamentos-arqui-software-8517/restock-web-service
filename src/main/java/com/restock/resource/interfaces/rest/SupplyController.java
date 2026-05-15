package com.restock.resource.interfaces.rest;

import com.restock.resource.application.internal.commandservices.SupplyCommandService;
import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.SupplyRepository;
import com.restock.resource.interfaces.rest.resources.CreateSupplyResource;
import com.restock.resource.interfaces.rest.resources.SupplyResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplies")
@Tag(name = "Resource - Inventory", description = "Inventory management and batch tracking")
@CrossOrigin(origins = "*")
public class SupplyController {

    private final SupplyCommandService commandService;
    private final SupplyRepository supplyRepository;

    public SupplyController(SupplyCommandService commandService, SupplyRepository supplyRepository) {
        this.commandService = commandService;
        this.supplyRepository = supplyRepository;
    }

    @Operation(summary = "Get all supplies")
    @GetMapping
    public ResponseEntity<List<SupplyResource>> getAll() {
        return ResponseEntity.ok(supplyRepository.findAll().stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get supply by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SupplyResource> getById(@PathVariable String id) {
        return supplyRepository.findById(id)
            .map(s -> ResponseEntity.ok(toResource(s)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new supply")
    @PostMapping
    public ResponseEntity<SupplyResource> create(@Valid @RequestBody CreateSupplyResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update a supply")
    @PutMapping("/{id}")
    public ResponseEntity<SupplyResource> update(@PathVariable String id,
                                                  @Valid @RequestBody CreateSupplyResource resource) {
        return commandService.update(id, resource)
            .map(s -> ResponseEntity.ok(toResource(s)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a supply")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private SupplyResource toResource(Supply s) {
        return new SupplyResource(s.getId(), s.getName(), s.getDescription(), s.getCategory(), s.getUomLabel(), s.isPerishable());
    }
}
