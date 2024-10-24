package com.returdev.gym_exercises_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing exercise details in the response.
 *
 * <p>
 * This record holds the information about an exercise, including its ID,
 * name, description, associated equipment, and the list of muscle engagements
 * involved in the exercise.
 * </p>
 *
 * @param id The unique identifier for the exercise.
 * @param name The name of the exercise.
 * @param description A brief description of the exercise.
 * @param equipment The equipment associated with the exercise.
 * @param muscleEngagements A list of muscle engagements involved in the exercise.
 */
public record ExerciseResponseDTO(
        Long id,
        String name,
        String description,
        EquipmentResponseDTO equipment,
        @JsonProperty("muscle_engagements")
        List<MuscleEngagementResponseDTO> muscleEngagements
) {}

