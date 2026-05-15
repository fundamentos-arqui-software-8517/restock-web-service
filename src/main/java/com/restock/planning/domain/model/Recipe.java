package com.restock.planning.domain.model;

import com.restock.shared.domain.model.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipes")
public class Recipe extends AggregateRoot {
    private String businessId;
    private String name;
    private String description;
    private String category;
    private List<RecipeIngredient> ingredients;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeIngredient {
        private String supplyId;
        private String supplyName;
        private double quantity;
        private String uomLabel;
    }
}
