package com.restock.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Sign-in request payload")
public record SignInResource(
    @Email @NotBlank @Schema(example = "user@example.com") String email,
    @NotBlank @Schema(example = "secret123") String password
) {}
