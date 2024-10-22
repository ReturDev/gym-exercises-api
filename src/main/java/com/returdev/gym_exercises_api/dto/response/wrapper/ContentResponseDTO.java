package com.returdev.gym_exercises_api.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ContentResponseDTO<T>(
        @JsonProperty("data") T content
) {}
