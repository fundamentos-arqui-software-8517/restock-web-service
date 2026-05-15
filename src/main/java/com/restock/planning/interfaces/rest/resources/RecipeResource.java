package com.restock.planning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Recipe response")
public record RecipeResource(
    String id,
    String businessId,
    String name,
    String description,
    String category,
    List<RecipeIngredientResource> ingredients
) {}
