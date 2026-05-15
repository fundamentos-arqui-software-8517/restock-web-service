package com.restock.profiles.interfaces.rest;

import com.restock.profiles.application.internal.commandservices.BusinessCommandService;
import com.restock.profiles.application.internal.queryservices.BusinessQueryService;
import com.restock.profiles.interfaces.rest.resources.BusinessResource;
import com.restock.profiles.interfaces.rest.resources.CreateBusinessResource;
import com.restock.profiles.interfaces.rest.transform.BusinessAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesses")
@Tag(name = "Profiles", description = "User profile management")
@CrossOrigin(origins = "*")
public class BusinessController {

    private final BusinessCommandService commandService;
    private final BusinessQueryService queryService;
    private final BusinessAssembler assembler;

    public BusinessController(BusinessCommandService commandService,
                               BusinessQueryService queryService,
                               BusinessAssembler assembler) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all businesses")
    @GetMapping
    public ResponseEntity<List<BusinessResource>> getAll() {
        return ResponseEntity.ok(queryService.findAll().stream().map(assembler::toResource).toList());
    }

    @Operation(summary = "Get business by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BusinessResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(assembler::toResource)
            .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new business")
    @PostMapping
    public ResponseEntity<BusinessResource> create(@Valid @RequestBody CreateBusinessResource resource) {
        return ResponseEntity.ok(assembler.toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update an existing business")
    @PutMapping("/{id}")
    public ResponseEntity<BusinessResource> update(@PathVariable String id,
                                                    @Valid @RequestBody CreateBusinessResource resource) {
        return commandService.update(id, resource).map(assembler::toResource)
            .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a business")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
