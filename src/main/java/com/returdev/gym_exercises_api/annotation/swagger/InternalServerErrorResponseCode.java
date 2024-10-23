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
 * Custom annotation to document a method that returns a 500 Internal Server Error response
 * in an OpenAPI specification using Swagger.
 * <p>
 * This annotation indicates that the API endpoint may result in a 500 status code,
 * which signifies that an unexpected condition was encountered on the server.
 * </p>
 * <p>
 * The annotation includes the following properties:
 * <ul>
 *   <li><strong>responseCode:</strong> The HTTP status code "500", indicating an internal server error.</li>
 *   <li><strong>description:</strong> A description of the error condition, which defaults to "Internal Server Error: An unexpected condition was encountered."</li>
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
 * @InternalServerErrorResponseCode
 * public ResponseEntity<Item> getItem(Long id) {
 *     return itemService.findItemById(id);
 * }
 * }</pre>
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "500",
        description = "Internal Server Error: An unexpected condition was encountered.",
        content = @Content(
                schema = @Schema(
                        implementation = ErrorResponseDTO.class,
                        description = "Error response object containing details of the internal server error."
                )
        )
)
public @interface InternalServerErrorResponseCode {}

