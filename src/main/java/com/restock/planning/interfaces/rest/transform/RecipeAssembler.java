package com.restock.planning.interfaces.rest.transform;

import com.restock.planning.domain.model.Recipe;
import com.restock.planning.interfaces.rest.resources.CreateRecipeResource;
import com.restock.planning.interfaces.rest.resources.RecipeIngredientResource;
import com.restock.planning.interfaces.rest.resources.RecipeResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeAssembler {

    public RecipeResource toResource(Recipe recipe) {
        List<RecipeIngredientResource> ingredients = recipe.getIngredients() == null ? List.of() :
            recipe.getIngredients().stream()
                .map(i -> new RecipeIngredientResource(i.getSupplyId(), i.getSupplyName(), i.getQuantity(), i.getUomLabel()))
                .toList();
        return new RecipeResource(recipe.getId(), recipe.getBusinessId(), recipe.getName(),
            recipe.getDescription(), recipe.getCategory(), ingredients);
    }

    public Recipe toEntity(CreateRecipeResource resource) {
        List<Recipe.RecipeIngredient> ingredients = resource.ingredients() == null ? List.of() :
            resource.ingredients().stream()
                .map(i -> Recipe.RecipeIngredient.builder()
                    .supplyId(i.supplyId()).supplyName(i.supplyName())
                    .quantity(i.quantity()).uomLabel(i.uomLabel()).build())
                .toList();
        return Recipe.builder()
            .businessId(resource.businessId()).name(resource.name())
            .description(resource.description()).category(resource.category())
            .ingredients(ingredients).build();
    }
}
