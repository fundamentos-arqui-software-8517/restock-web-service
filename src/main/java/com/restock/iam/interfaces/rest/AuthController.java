package com.restock.iam.interfaces.rest;

import com.restock.iam.application.internal.commandservices.UserCommandService;
import com.restock.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.restock.iam.interfaces.rest.resources.SignInResource;
import com.restock.iam.interfaces.rest.resources.SignUpResource;
import com.restock.iam.interfaces.rest.transform.UserAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "IAM - Authentication", description = "User sign-up and sign-in")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserCommandService commandService;
    private final UserAssembler assembler;

    public AuthController(UserCommandService commandService, UserAssembler assembler) {
        this.commandService = commandService;
        this.assembler = assembler;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticatedUserResource> signUp(@Valid @RequestBody SignUpResource resource) {
        try {
            return ResponseEntity.ok(assembler.toResource(commandService.register(resource)));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Authenticate an existing user")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@Valid @RequestBody SignInResource resource) {
        return commandService.authenticate(resource)
            .map(assembler::toResource)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(401).build());
    }
}
