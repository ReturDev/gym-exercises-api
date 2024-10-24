package com.returdev.gym_exercises_api.annotation.swagger.response;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to document a method that returns a 401 Unauthorized response
 * in an OpenAPI specification using Swagger.
 * <p>
 * This annotation indicates that the client is not authorized to perform the requested action,
 * typically due to missing or invalid authentication credentials.
 * </p>
 * <p>
 * The annotation includes the following properties:
 * <ul>
 *   <li><strong>responseCode:</strong> The HTTP status code "401", indicating that the request requires user authentication.</li>
 *   <li><strong>description:</strong> A description stating "Unauthorized: The client is not authorized to perform this action."</li>
 *   <li><strong>content:</strong> Specifies that the response content adheres to the schema defined.</li>
 * </ul>
 * </p>
 * <p>
 * This annotation is processed at runtime and is primarily used for generating API documentation
 * with OpenAPI via Swagger.
 * </p>
 * <p>
 * <strong>Example usage:</strong>
 * <pre>{@code
 * @UnauthorizedResponseCode
 * public ResponseEntity<Item> performAction() {
 *     // Check user authorization
 *     if (!userIsAuthorized()) {
 *         throw new UnauthorizedActionException("User is not authorized to perform this action.");
 *     }
 *     // Perform the action
 *     return ResponseEntity.ok(item);
 * }
 * }</pre>
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "401",
        description = "Unauthorized: The client is not authorized to perform this action.",
        content = @Content(
                schema = @Schema(
                        ref = "#/components/schemas/ErrorResponse",
                        description = "Error response object containing details about the unauthorized request."
                )
        )
)
public @interface UnauthorizedResponseCode {}

