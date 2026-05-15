package com.restock.planning.infrastructure.persistence;

import com.restock.planning.domain.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoRecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByBusinessId(String businessId);
    List<Recipe> findByCategory(String category);
}
