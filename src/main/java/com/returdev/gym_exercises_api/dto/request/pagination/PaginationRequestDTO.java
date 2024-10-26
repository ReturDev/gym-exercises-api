package com.returdev.gym_exercises_api.dto.request.pagination;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.data.domain.Sort;

/**
 * An abstract base class for pagination request data transfer objects (DTOs).
 *
 * <p>
 * This class encapsulates the common pagination parameters such as page number,
 * page size, and sort direction. It provides default values for these parameters
 * and includes validation annotations to ensure they adhere to specified constraints.
 * Subclasses should implement the {@link #getOrderBy()} method to define the field by
 * which the results should be sorted.
 * </p>
 */
@Getter
public sealed abstract class PaginationRequestDTO permits EquipmentPaginationRequestDTO, ExercisePaginationRequestDTO {

    // Default values for pagination
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 25;
    public static final String DEFAULT_SORT_DIRECTION = Sort.Direction.ASC.name();

    /**
     * The page number to retrieve. Must be at least 1.
     */
    @Min(value = 1, message = "{validation.min_value.message}")
    protected Integer page;

    /**
     * The size of the page to retrieve. Must be at least 1 and no greater than the default page size.
     */
    @Min(value = 1, message = "{validation.min_value.message}")
    @Max(value = DEFAULT_PAGE_SIZE, message = "{validation.max_value.message}")
    protected Integer pageSize;

    /**
     * The direction to sort the results. Can be either "ASC" or "DESC".
     */
    @Pattern(regexp = "(?i)ASC|DESC", message = "{validation.pagination_request_dto.sort_direction.message}")
    protected String sortDirection;

    /**
     * Constructs a new instance of {@link PaginationRequestDTO}.
     *
     * @param page The page number to retrieve (default is 1 if null).
     * @param pageSize The size of the page to retrieve (default is 25 if null).
     * @param sortDirection The direction to sort the results (default is "ASC" if null).
     */
    protected PaginationRequestDTO(Integer page, Integer pageSize, String sortDirection) {
        this.page = (page != null) ? page : DEFAULT_PAGE;
        this.pageSize = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
        this.sortDirection = (sortDirection != null) ? sortDirection : DEFAULT_SORT_DIRECTION;
    }

    /**
     * Abstract method to retrieve the field by which to order the results.
     * Subclasses must provide an implementation for this method.
     *
     * @return The field name to order by.
     */
    public abstract String getOrderBy();
}

