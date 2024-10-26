package com.returdev.gym_exercises_api.dto.request.pagination;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

/**
 * DTO for pagination requests specifically for exercises.
 *
 * <p>
 * This class extends {@link PaginationRequestDTO} to provide pagination parameters
 * tailored for exercise-related queries. It includes an ordering option that specifies
 * how the results should be sorted based on the exercise's attributes.
 * </p>
 */
public final class ExercisePaginationRequestDTO extends PaginationRequestDTO {

    /**
     * The field by which to order the results. Can be "ID", "EXERCISE_NAME", or "EQUIPMENT_NAME".
     */
    @Pattern(regexp = "(?i)ID|EXERCISE_NAME|EQUIPMENT_NAME", message = "{validation.exercise_pagination_request_dto.order_by.message}")
    private final String orderBy;

    /**
     * Constructs a new instance of {@link ExercisePaginationRequestDTO}.
     *
     * @param page The page number to retrieve (default is set by parent).
     * @param pageSize The size of the page to retrieve (default is set by parent).
     * @param sortDirection The direction to sort the results (default is set by parent).
     * @param orderBy The field to order by (defaults to ID if null).
     */
    public ExercisePaginationRequestDTO(
            Integer page,
            Integer pageSize,
            String sortDirection,
            String orderBy
    ) {
        super(page, pageSize, sortDirection);
        this.orderBy = (orderBy != null) ? orderBy : ExerciseOrderByField.ID.name();
    }

    /**
     * Retrieves the field name by which to order the results.
     *
     * @return The corresponding entity property name.
     */
    @Override
    public String getOrderBy() {
        return ExerciseOrderByField.fromString(orderBy);
    }

    /**
     * Enum representing the fields by which exercises can be ordered.
     */
    @AllArgsConstructor
    private enum ExerciseOrderByField {

        ID("id"),
        EXERCISE_NAME("name"),
        EQUIPMENT_NAME("equipment_name");

        public final String entityPropertyName;

        /**
         * Converts a string to the corresponding {@link ExerciseOrderByField} enum.
         *
         * @param value The string representation of the enum value.
         * @return The entity property name associated with the enum.
         */
        public static String fromString(String value) {
            return valueOf(value.toUpperCase()).entityPropertyName;
        }

    }

}

