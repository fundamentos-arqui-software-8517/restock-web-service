package com.restock.sales.interfaces.rest;

import com.restock.sales.application.internal.commandservices.SaleCommandService;
import com.restock.sales.application.internal.queryservices.SaleQueryService;
import com.restock.sales.domain.model.Sale;
import com.restock.sales.interfaces.rest.resources.CreateSaleResource;
import com.restock.sales.interfaces.rest.resources.SaleItemResource;
import com.restock.sales.interfaces.rest.resources.SaleResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@Tag(name = "Sales", description = "Sales transaction management")
@CrossOrigin(origins = "*")
public class SaleController {

    private final SaleCommandService commandService;
    private final SaleQueryService queryService;

    public SaleController(SaleCommandService commandService, SaleQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all sales")
    @GetMapping
    public ResponseEntity<List<SaleResource>> getAll(@RequestParam(required = false) String businessId) {
        List<Sale> results = businessId != null ? queryService.findByBusinessId(businessId) : queryService.findAll();
        return ResponseEntity.ok(results.stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get sale by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SaleResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(s -> ResponseEntity.ok(toResource(s))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Register a new sale")
    @PostMapping
    public ResponseEntity<SaleResource> create(@Valid @RequestBody CreateSaleResource resource) {
        return ResponseEntity.ok(toResource(commandService.register(resource)));
    }

    private SaleResource toResource(Sale s) {
        List<SaleItemResource> items = s.getItems() == null ? List.of() :
            s.getItems().stream().map(i -> new SaleItemResource(i.getSupplyId(), i.getSupplyName(), i.getQuantity(), i.getUnitPrice(), i.getSubtotal())).toList();
        return new SaleResource(s.getId(), s.getBusinessId(), s.getBranchId(), s.getSoldAt(), s.getTotalAmount(), items);
    }
}
