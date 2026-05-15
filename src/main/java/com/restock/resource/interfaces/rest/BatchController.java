package com.restock.resource.interfaces.rest;

import com.restock.resource.application.internal.commandservices.BatchCommandService;
import com.restock.resource.domain.model.Batch;
import com.restock.resource.interfaces.rest.resources.BatchResource;
import com.restock.resource.interfaces.rest.resources.CreateBatchResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batches")
@Tag(name = "Resource - Inventory", description = "Inventory management and batch tracking")
@CrossOrigin(origins = "*")
public class BatchController {

    private final BatchCommandService commandService;

    public BatchController(BatchCommandService commandService) {
        this.commandService = commandService;
    }

    @Operation(summary = "Get all batches")
    @GetMapping
    public ResponseEntity<List<BatchResource>> getAll() {
        return ResponseEntity.ok(commandService.findAll().stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get batch by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BatchResource> getById(@PathVariable String id) {
        return commandService.findById(id)
            .map(b -> ResponseEntity.ok(toResource(b)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new batch")
    @PostMapping
    public ResponseEntity<BatchResource> create(@Valid @RequestBody CreateBatchResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update a batch")
    @PutMapping("/{id}")
    public ResponseEntity<BatchResource> update(@PathVariable String id,
                                                 @Valid @RequestBody CreateBatchResource resource) {
        return commandService.update(id, resource)
            .map(b -> ResponseEntity.ok(toResource(b)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a batch")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private BatchResource toResource(Batch b) {
        return new BatchResource(b.getId(), b.getSupplyId(), b.getBranchId(),
            b.getCurrentQuantity(), b.getExpirationDate(), b.getMinStock(), b.getMaxStock());
    }
}
