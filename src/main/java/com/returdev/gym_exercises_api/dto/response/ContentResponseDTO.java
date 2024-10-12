package com.returdev.gym_exercises_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDTO<T> {

    @JsonProperty("data")
    private T content;

    @JsonProperty("pageInfo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageInfo pageInfo;


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
