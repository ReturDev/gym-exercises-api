package com.returdev.gym_exercises_api.dto.request.pagination;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

public class ExercisePaginationRequestDTO extends PaginationRequestDTO {

    @Pattern(regexp = "(?i)ID|EXERCISE_NAME|EQUIPMENT_NAME", message = "{validation.exercise_pagination_request_dto.order_by.message}")
    private final String orderBy;

    public ExercisePaginationRequestDTO(
            Integer page,
            Integer pageSize,
            String sortDirection,
            String orderBy
    ) {
        super(page, pageSize, sortDirection);
        this.orderBy = (orderBy != null) ? orderBy : ExerciseOrderByField.ID.name();
    }

    @Override
    public String getOrderBy() {
        return ExerciseOrderByField.fromString(orderBy);
    }

    @AllArgsConstructor
    private enum ExerciseOrderByField {

        ID("id"),
        EXERCISE_NAME("name"),
        EQUIPMENT_NAME("equipment_name");

        public final String entityPropertyName;

        public static String fromString(String value) {
            return valueOf(value.toUpperCase()).entityPropertyName;
        }

    }


}
