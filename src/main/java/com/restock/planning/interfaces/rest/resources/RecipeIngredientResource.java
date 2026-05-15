package com.restock.planning.interfaces.rest.resources;

public record RecipeIngredientResource(
    String supplyId,
    String supplyName,
    double quantity,
    String uomLabel
) {}
