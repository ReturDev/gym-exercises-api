package com.returdev.gym_exercises_api.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


public record PaginationResponseDTO<T>(
        @JsonProperty("data") List<T> content,
        @JsonProperty("pageInfo") com.returdev.gym_exercises_api.dto.response.wrapper.PaginationResponseDTO.PageInfo pageInfo
) {

    @Data
    @AllArgsConstructor
    public static class PageInfo {
        @JsonProperty(value = "size")
        private int pageSize;
        @JsonProperty(value = "totalElements")
        private long totalElements;
        @JsonProperty(value = "totalPages")
        private int totalPages;
        @JsonProperty(value = "number")
        private int pageNumber;
    }

}
