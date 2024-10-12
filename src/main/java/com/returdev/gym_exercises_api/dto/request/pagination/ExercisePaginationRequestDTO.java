package com.returdev.gym_exercises_api.dto.request.pagination;

import jakarta.validation.constraints.Pattern;

public class ExercisePaginationRequestDTO extends PaginationRequestDTO {

    @Pattern(regexp = "(?i)ID|NAME|EQUIPMENT_NAME")
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

    private enum ExerciseOrderByField {

        ID("id"),
        NAME("name"),
        EQUIPMENT_NAME("equipment_name");

        public final String entityPropertyName;

        ExerciseOrderByField(String entityPropertyName) {
            this.entityPropertyName = entityPropertyName;
        }

        public static String fromString(String value) {
            return valueOf(value.toUpperCase()).entityPropertyName;
        }

    }


}
