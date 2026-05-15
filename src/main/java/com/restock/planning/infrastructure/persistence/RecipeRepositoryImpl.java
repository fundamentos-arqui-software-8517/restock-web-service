package com.restock.planning.infrastructure.persistence;

import com.restock.planning.domain.model.Recipe;
import com.restock.planning.domain.repositories.RecipeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

    private final MongoRecipeRepository mongo;

    public RecipeRepositoryImpl(MongoRecipeRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Recipe> findAll() { return mongo.findAll(); }
    @Override public Optional<Recipe> findById(String id) { return mongo.findById(id); }
    @Override public List<Recipe> findByBusinessId(String businessId) { return mongo.findByBusinessId(businessId); }
    @Override public List<Recipe> findByCategory(String category) { return mongo.findByCategory(category); }
    @Override public Recipe save(Recipe recipe) { return mongo.save(recipe); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
}
