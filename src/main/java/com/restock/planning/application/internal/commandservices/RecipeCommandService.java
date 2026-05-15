package com.restock.planning.application.internal.commandservices;

import com.restock.planning.domain.model.Recipe;
import com.restock.planning.domain.repositories.RecipeRepository;
import com.restock.planning.interfaces.rest.resources.CreateRecipeResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCommandService {

    private final RecipeRepository recipeRepository;

    public RecipeCommandService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe create(CreateRecipeResource resource) {
        List<Recipe.RecipeIngredient> ingredients = resource.ingredients() == null ? List.of() :
            resource.ingredients().stream()
                .map(i -> Recipe.RecipeIngredient.builder()
                    .supplyId(i.supplyId()).supplyName(i.supplyName())
                    .quantity(i.quantity()).uomLabel(i.uomLabel()).build())
                .toList();
        Recipe recipe = Recipe.builder()
            .businessId(resource.businessId())
            .name(resource.name())
            .description(resource.description())
            .category(resource.category())
            .ingredients(ingredients)
            .build();
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> update(String id, CreateRecipeResource resource) {
        return recipeRepository.findById(id).map(existing -> {
            existing.setName(resource.name());
            existing.setDescription(resource.description());
            existing.setCategory(resource.category());
            return recipeRepository.save(existing);
        });
    }

    public void delete(String id) {
        recipeRepository.deleteById(id);
    }
}
