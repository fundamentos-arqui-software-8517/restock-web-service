package com.restock.planning.application.internal.queryservices;

import com.restock.planning.domain.model.Recipe;
import com.restock.planning.domain.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeQueryService {

    private final RecipeRepository recipeRepository;

    public RecipeQueryService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAll() { return recipeRepository.findAll(); }
    public Optional<Recipe> findById(String id) { return recipeRepository.findById(id); }
    public List<Recipe> findByBusinessId(String businessId) { return recipeRepository.findByBusinessId(businessId); }
}
