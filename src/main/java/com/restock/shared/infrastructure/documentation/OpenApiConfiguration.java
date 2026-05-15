package com.restock.shared.infrastructure.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Restock API",
        version = "1.0.0",
        description = "Restock IoT Inventory Management REST API — Modular Monolith + DDD",
        contact = @Contact(name = "Restock Team")
    ),
    servers = @Server(url = "http://localhost:8080", description = "Local Development")
)
public class OpenApiConfiguration {
}
