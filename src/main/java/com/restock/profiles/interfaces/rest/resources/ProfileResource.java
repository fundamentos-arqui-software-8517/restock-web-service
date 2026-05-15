package com.restock.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User profile response")
public record ProfileResource(
    @JsonProperty("_id") String id,
    @JsonProperty("user_id") String userId,
    String name,
    @JsonProperty("last_name") String lastName,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("avatar_url") String avatarUrl,
    String gender,
    @JsonProperty("birth_date") String birthDate
) {}
