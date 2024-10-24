package com.returdev.gym_exercises_api.annotation.swagger.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Custom annotation to define pagination and sorting parameters for exercise-related API requests in OpenAPI documentation.
 * <p>
 * This annotation can be applied to methods to specify the expected query parameters for pagination,
 * such as page number, page size, sorting direction, and sorting field. It is primarily used
 * to generate API documentation via OpenAPI for endpoints that handle paginated requests.
 * <p>
 * The annotation includes the following parameters:
 * <ul>
 *   <li><b>page:</b> Indicates the page number to retrieve, starting from 1.<br>
 *       It has a minimum value of 1 and defaults to the first page if not specified.</li>
 *   <li><b>pageSize:</b> Specifies the number of items per page, with a maximum of 25.<br>
 *       Defaults to 25 if not provided, and must be at least 1.</li>
 *   <li><b>sortDirection:</b> Determines the direction of sorting, either ascending ("asc") or descending ("desc").<br>
 *       This field controls the order of the results returned.</li>
 *   <li><b>orderBy:</b> Specifies the field by which the results should be sorted.<br>
 *       Allowed values include "id" and "name".</li>
 * </ul>
 * <p>
 * This annotation is processed at runtime and enhances API documentation for methods that handle paginated exercise queries.
 * <p>
 * <b>Example usage:</b>
 * <pre>{@code
 * @ExercisePaginationRequestParameters
 * public PaginationResponseDTO<ExerciseResponseDTO> getExercises(
 *     @RequestParam("page") int page,
 *     @RequestParam("pageSize") int pageSize,
 *     @RequestParam("sortDirection") String sortDirection,
 *     @RequestParam("orderBy") String orderBy
 * ) {
 *     // Method implementation here
 * }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameters(
        value = {
                @Parameter(
                        name = "page",
                        description = "The page number to retrieve (starting from 1).",
                        schema = @Schema(
                                type = "Integer",
                                minimum = "1"
                        )
                ),
                @Parameter(
                        name = "pageSize",
                        description = "Number of items per page, with a maximum of 25. Defaults to 25 if not specified.",
                        schema = @Schema(
                                type = "Integer",
                                minimum = "1",
                                maximum = "25"
                        )
                ),
                @Parameter(
                        name = "sortDirection",
                        description = "Direction of sorting.",
                        schema = @Schema(
                                type = "String",
                                allowableValues = {"asc", "desc"}
                        )
                ),
                @Parameter(
                        name = "orderBy",
                        description = "Field to sort by.",
                        schema = @Schema(
                                type = "String",
                                allowableValues = {"id", "name"}
                        )
                )
        }
)
public @interface ExercisePaginationRequestParameters {}
