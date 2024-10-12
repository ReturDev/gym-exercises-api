package com.returdev.gym_exercises_api.dto.request.pagination;

import jakarta.validation.constraints.Pattern;

public class EquipmentPaginationRequestDTO extends PaginationRequestDTO {

    @Pattern(regexp = "(?i)ID|NAME") private final String orderBy;

    public EquipmentPaginationRequestDTO(
            Integer page,
            Integer pageSize,
            String sortDirection,
            String orderBy
    ) {
        super(page,pageSize,sortDirection);
        this.orderBy = (orderBy != null) ? orderBy : EquipmentOrderByField.ID.name();
    }

    @Override
    public String getOrderBy() {
        return EquipmentOrderByField.fromString(orderBy);
    }

    private enum EquipmentOrderByField {

        ID("id"),
        NAME("name");

        public final String entityPropertyName;

        EquipmentOrderByField(String entityPropertyName) {
            this.entityPropertyName = entityPropertyName;
        }

        public static String fromString(String value) {
            return valueOf(value.toUpperCase()).entityPropertyName;
        }

    }

}
