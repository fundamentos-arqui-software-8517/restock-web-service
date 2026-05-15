package com.restock.planning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Schema(description = "Create or update recipe request")
public record CreateRecipeResource(
    @NotBlank String businessId,
    @NotBlank String name,
    String description,
    String category,
    List<RecipeIngredientResource> ingredients
) {}
