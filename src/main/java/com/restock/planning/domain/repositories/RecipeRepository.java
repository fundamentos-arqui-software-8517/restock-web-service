package com.restock.planning.domain.repositories;

import com.restock.planning.domain.model.Recipe;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for recipe persistence. */
public interface RecipeRepository {
    List<Recipe> findAll();
    Optional<Recipe> findById(String id);
    List<Recipe> findByBusinessId(String businessId);
    List<Recipe> findByCategory(String category);
    Recipe save(Recipe recipe);
    void deleteById(String id);
}
