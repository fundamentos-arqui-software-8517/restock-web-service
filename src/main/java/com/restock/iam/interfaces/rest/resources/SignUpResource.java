package com.restock.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Sign-up request payload")
public record SignUpResource(
    @Email @NotBlank @Schema(example = "user@example.com") String email,
    @NotBlank @Size(min = 6) @Schema(example = "secret123") String password
) {}
