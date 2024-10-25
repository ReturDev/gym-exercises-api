package com.returdev.gym_exercises_api.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * A generic Data Transfer Object (DTO) for wrapping a single content item
 * in the response.
 *
 * <p>
 * This record is designed to encapsulate any type of content, allowing
 * for flexible response structures. It can be used to return a single
 * entity or data object as part of a broader API response.
 * </p>
 *
 * @param <T> The type of content being wrapped.
 * @param content The content of type {@code T} to be included in the response.
 */
@Schema(name = "Content")
public record ContentResponseDTO<T>(
        @JsonProperty("data") T content
) {}

