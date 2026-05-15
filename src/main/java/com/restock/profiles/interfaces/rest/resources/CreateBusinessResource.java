package com.restock.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Create or update business request")
public record CreateBusinessResource(
    @JsonProperty("user_id") @NotBlank String userId,
    @NotBlank String ruc,
    @JsonProperty("picture_url") String pictureUrl,
    @JsonProperty("company_name") @NotBlank String companyName,
    @JsonProperty("main_location") String mainLocation
) {}
