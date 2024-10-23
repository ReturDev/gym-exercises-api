package com.returdev.gym_exercises_api.annotation.swagger;

import com.returdev.gym_exercises_api.dto.response.error.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to document a method that returns a 409 Conflict response
 * in an OpenAPI specification using Swagger.
 * <p>
 * This annotation indicates that the API endpoint may result in a 409 status code,
 * which signifies that the request could not be completed due to a conflict with the current state
 * of the resource.
 * </p>
 * <p>
 * The annotation includes the following properties:
 * <ul>
 *   <li><strong>responseCode:</strong> The HTTP status code "409", indicating a conflict.</li>
 *   <li><strong>description:</strong> A description of the conflict condition, which defaults to "Conflict".</li>
 *   <li><strong>content:</strong> Specifies that the response content will adhere to the schema defined by {@link ErrorResponseDTO}.</li>
 * </ul>
 * </p>
 * <p>
 * This annotation is processed at runtime and is primarily used for generating API documentation
 * with OpenAPI via Swagger.
 * </p>
 * <p>
 * <strong>Example usage:</strong>
 * <pre>{@code
 * @ConflictResponseCode
 * public ResponseEntity<Item> createItem(Item item) {
 *     return itemService.createItem(item);
 * }
 * }</pre>
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "409",
        description = "Conflict: The request could not be completed due to a conflict with the current state of the resource.",
        content = @Content(
                schema = @Schema(
                        implementation = ErrorResponseDTO.class,
                        description = "Error response object containing details about the conflict."
                )
        )
)
public @interface ConflictResponseCode {}

