package com.returdev.gym_exercises_api.annotation.swagger.response;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to document a method that returns a successful HTTP 200 response in an OpenAPI specification using Swagger.
 * <p>
 * This annotation indicates that the API endpoint returns an HTTP 200 status code,
 * signifying that the request was processed successfully.
 * </p>
 * <p>
 * The annotation includes the following properties:
 * <ul>
 *   <li><strong>responseCode:</strong> The HTTP status code "200", indicating a successful operation.</li>
 *   <li><strong>description:</strong> A brief description of the outcome, which defaults to "Retrieved successfully".</li>
 *   <li><strong>useReturnTypeSchema:</strong> Indicates that the response schema should be inferred from the method's return type.</li>
 * </ul>
 * </p>
 * <p>
 * This annotation is processed at runtime and is primarily used for generating API documentation
 * with OpenAPI via Swagger.
 * </p>
 * <p>
 * <strong>Example usage:</strong>
 * <pre>{@code
 * @OkResponseCode
 * public List<Item> getItems() {
 *     return itemService.getAllItems();
 * }
 * }</pre>
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "200",
        description = "Retrieved successfully",
        useReturnTypeSchema = true
)
public @interface OkResponseCode {}
