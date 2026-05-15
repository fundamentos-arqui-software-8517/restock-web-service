package com.restock.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Business profile response")
public record BusinessResource(
    @JsonProperty("_id") String id,
    @JsonProperty("user_id") String userId,
    String ruc,
    @JsonProperty("picture_url") String pictureUrl,
    @JsonProperty("company_name") String companyName,
    @JsonProperty("main_location") String mainLocation
) {}
