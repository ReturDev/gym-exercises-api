package com.returdev.gym_exercises_api.annotation.swagger.response;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to document a method that returns a 204 No Content response
 * in an OpenAPI specification using Swagger.
 * <p>
 * This annotation indicates that the API endpoint has successfully processed the request
 * but does not have any content to return in the response body.
 * </p>
 * <p>
 * The annotation includes the following properties:
 * <ul>
 *   <li><strong>responseCode:</strong> The HTTP status code "204", indicating that the request was successful but there is no content to send.</li>
 *   <li><strong>description:</strong> A brief description stating "No Content".</li>
 * </ul>
 * </p>
 * <p>
 * This annotation is processed at runtime and is primarily used for generating API documentation
 * with OpenAPI via Swagger.
 * </p>
 * <p>
 * <strong>Example usage:</strong>
 * <pre>{@code
 * @NoContentResponseCode
 * public ResponseEntity<Void> deleteItem(Long id) {
 *     itemService.deleteItem(id);
 *     return ResponseEntity.noContent().build();
 * }
 * }</pre>
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "204",
        description = "No Content: The request was successful but there is no content to return."
)
public @interface NoContentResponseCode {}

