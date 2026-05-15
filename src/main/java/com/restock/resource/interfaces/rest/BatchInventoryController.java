package com.restock.resource.interfaces.rest;

import com.restock.resource.application.internal.queryservices.BatchInventoryQueryService;
import com.restock.resource.interfaces.rest.resources.BatchInventoryRootResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Resource - Inventory", description = "Inventory management and batch tracking")
@CrossOrigin(origins = "*")
public class BatchInventoryController {

    private final BatchInventoryQueryService queryService;

    public BatchInventoryController(BatchInventoryQueryService queryService) {
        this.queryService = queryService;
    }

    @Operation(summary = "Get batch inventory snapshot — matches frontend contract")
    @GetMapping("/batch-inventory")
    public ResponseEntity<BatchInventoryRootResource> getBatchInventory() {
        return ResponseEntity.ok(queryService.getBatchInventory());
    }
}
