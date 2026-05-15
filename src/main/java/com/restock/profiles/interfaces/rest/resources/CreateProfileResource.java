package com.restock.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Create or update profile request")
public record CreateProfileResource(
    @JsonProperty("user_id") @NotBlank String userId,
    @NotBlank String name,
    @JsonProperty("last_name") String lastName,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("avatar_url") String avatarUrl,
    String gender,
    @JsonProperty("birth_date") String birthDate
) {}
