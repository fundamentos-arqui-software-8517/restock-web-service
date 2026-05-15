package com.restock.profiles.interfaces.rest;

import com.restock.profiles.application.internal.commandservices.ProfileCommandService;
import com.restock.profiles.application.internal.queryservices.ProfileQueryService;
import com.restock.profiles.interfaces.rest.resources.CreateProfileResource;
import com.restock.profiles.interfaces.rest.resources.ProfileResource;
import com.restock.profiles.interfaces.rest.transform.ProfileAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@Tag(name = "Profiles", description = "User profile management")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileCommandService commandService;
    private final ProfileQueryService queryService;
    private final ProfileAssembler assembler;

    public ProfileController(ProfileCommandService commandService,
                              ProfileQueryService queryService,
                              ProfileAssembler assembler) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all profiles")
    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAll() {
        return ResponseEntity.ok(queryService.findAll().stream().map(assembler::toResource).toList());
    }

    @Operation(summary = "Get profile by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(assembler::toResource)
            .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new profile")
    @PostMapping
    public ResponseEntity<ProfileResource> create(@Valid @RequestBody CreateProfileResource resource) {
        return ResponseEntity.ok(assembler.toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update an existing profile")
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResource> update(@PathVariable String id,
                                                   @Valid @RequestBody CreateProfileResource resource) {
        return commandService.update(id, resource).map(assembler::toResource)
            .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
