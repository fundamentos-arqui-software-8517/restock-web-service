package com.restock.planning.interfaces.rest;

import com.restock.planning.application.internal.commandservices.RecipeCommandService;
import com.restock.planning.application.internal.queryservices.RecipeQueryService;
import com.restock.planning.domain.model.Recipe;
import com.restock.planning.interfaces.rest.resources.CreateRecipeResource;
import com.restock.planning.interfaces.rest.resources.RecipeIngredientResource;
import com.restock.planning.interfaces.rest.resources.RecipeResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Planning - Recipes", description = "Recipe and production planning management")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeCommandService commandService;
    private final RecipeQueryService queryService;

    public RecipeController(RecipeCommandService commandService, RecipeQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all recipes")
    @GetMapping
    public ResponseEntity<List<RecipeResource>> getAll(@RequestParam(required = false) String businessId) {
        List<Recipe> results = businessId != null ? queryService.findByBusinessId(businessId) : queryService.findAll();
        return ResponseEntity.ok(results.stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get recipe by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(r -> ResponseEntity.ok(toResource(r))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new recipe")
    @PostMapping
    public ResponseEntity<RecipeResource> create(@Valid @RequestBody CreateRecipeResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Update a recipe")
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResource> update(@PathVariable String id, @Valid @RequestBody CreateRecipeResource resource) {
        return commandService.update(id, resource).map(r -> ResponseEntity.ok(toResource(r))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a recipe")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private RecipeResource toResource(Recipe r) {
        List<RecipeIngredientResource> ingredients = r.getIngredients() == null ? List.of() :
            r.getIngredients().stream().map(i -> new RecipeIngredientResource(i.getSupplyId(), i.getSupplyName(), i.getQuantity(), i.getUomLabel())).toList();
        return new RecipeResource(r.getId(), r.getBusinessId(), r.getName(), r.getDescription(), r.getCategory(), ingredients);
    }
}
