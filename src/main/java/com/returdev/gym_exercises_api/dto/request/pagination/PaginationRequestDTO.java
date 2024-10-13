package com.returdev.gym_exercises_api.dto.request.pagination;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.data.domain.Sort;


@Getter
public abstract class PaginationRequestDTO {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 25;
    public static final String DEFAULT_SORT_DIRECTION = Sort.Direction.ASC.name();

    @Min(value = 1, message = "{validation.min_value.message}")
    protected Integer page;
    @Min(value = 1, message = "{validation.min_value.message}")
    @Max(value = DEFAULT_PAGE_SIZE, message = "{validation.max_value.message}")
    protected Integer pageSize;
    @Pattern(regexp = "(?i)ASC|DESC", message = "{validation.pagination_request_dto.sort_direction.message}")
    protected String sortDirection;

    protected PaginationRequestDTO(Integer page, Integer pageSize, String sortDirection) {
        this.page = (page != null) ? page : DEFAULT_PAGE;
        this.pageSize = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
        this.sortDirection = (sortDirection != null) ? sortDirection : DEFAULT_SORT_DIRECTION;
    }

    abstract public String getOrderBy();

}
