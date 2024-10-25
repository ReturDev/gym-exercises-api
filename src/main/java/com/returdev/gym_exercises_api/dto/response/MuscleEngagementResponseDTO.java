package com.returdev.gym_exercises_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for representing muscle engagement details in the response.
 *
 * <p>
 * This record holds the information about a muscle engagement, including its ID,
 * the muscle involved, and the activation level of that muscle.
 * </p>
 *
 * @param id The unique identifier for the muscle engagement.
 * @param muscle The name of the muscle involved in the engagement.
 * @param muscleActivationLevel  The activation level of the muscle (e.g., HIGH, MEDIUM, LOW).
 */
@Schema(name = "MuscleEngagement")
public record MuscleEngagementResponseDTO(
        Long id,
        String muscle,
        @JsonProperty("muscle_activation_level")
        String muscleActivationLevel
) {}
