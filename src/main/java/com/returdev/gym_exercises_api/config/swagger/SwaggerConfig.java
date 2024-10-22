package com.returdev.gym_exercises_api.config.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger API documentation.
 *
 * <p>
 * This class uses OpenAPI annotations to define the API's metadata, security requirements,
 * and server information for the Gym Exercises API. It serves as the entry point for generating
 * the API documentation and providing clients with necessary details to interact with the service.
 * </p>
 *
 * <p>
 * The API supports Bearer Authentication using JWT tokens, which are required to access secured endpoints.
 * </p>
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Gym Exercises API",
                description = "The Gym Exercise API is a RESTful service designed to manage and track gym exercises and equipment efficiently." +
                        " It allows developers to interact with various endpoints related to workout routines, exercise details, and equipment management." +
                        " This API provides robust features for creating, updating, retrieving, and deleting exercises and equipment, with built-in authentication and authorization mechanisms to ensure secure access.",
                contact = @Contact(
                        name = "ReturDev",
                        url = "https://github.com/ReturDev",
                        email = "sergiomm50@gmail.com"
                ),
                version = "1.0.0"
        ),
        security = @SecurityRequirement(
                name = "Bearer Authentication"
        ),
        servers = {
                @Server(
                        description = "Development Server",
                        url = "http://localhost:8080/gym-exercises/api/"
                ),
                @Server(
                        description = "Production Server",
                        url = ""//TODO
                )
        }
)

@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "Bearer Authentication",
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Use a valid JWT token to access secured endpoints."

)
@Configuration
public class SwaggerConfig {}
