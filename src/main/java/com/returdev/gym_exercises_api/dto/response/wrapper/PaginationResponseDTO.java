package com.returdev.gym_exercises_api.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * A generic Data Transfer Object (DTO) for wrapping paginated response data.
 *
 * <p>
 * This record encapsulates a list of content items along with pagination
 * information, providing a structure to facilitate the representation of
 * paginated data in API responses. It is useful for cases where the
 * result set is too large to return in a single response and needs to
 * be broken into smaller pages.
 * </p>
 *
 * @param <T> The type of content being wrapped.
 * @param content A list of content items of type {@code T} for the current page.
 * @param pageInfo Pagination metadata including size, total elements, total pages, and current page number.
 */
public record PaginationResponseDTO<T>(
        @JsonProperty("data") List<T> content,
        @JsonProperty("pageInfo") PageInfo pageInfo
) {

    /**
     * A static inner class representing pagination information.
     *
     * <p>
     * This class encapsulates details about the pagination state,
     * such as the number of items per page, total elements available,
     * total pages, and the current page number. It provides a way to
     * convey the pagination context along with the content.
     * </p>
     */
    @Data
    @AllArgsConstructor
    public static class PageInfo {
        @JsonProperty(value = "size")
        private int pageSize;          // The number of items per page.

        @JsonProperty(value = "totalElements")
        private long totalElements;     // The total number of items available across all pages.

        @JsonProperty(value = "totalPages")
        private int totalPages;         // The total number of pages available.

        @JsonProperty(value = "number")
        private int pageNumber;         // The current page number (0-indexed).
    }
}

