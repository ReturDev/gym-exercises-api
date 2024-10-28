package com.returdev.gym_exercises_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * A Data Transfer Object (DTO) representing a request to create or update an exercise.
 *
 * <p>
 * This record is used to encapsulate the necessary information required
 * for creating or updating an exercise in an API request. It includes
 * validation annotations to ensure that the provided values adhere to
 * specific constraints, ensuring data integrity when processing exercise
 * related requests.
 * </p>
 *
 * @param id The unique identifier of the exercise. Can be null for new exercises.
 * @param name The name of the exercise. Must not be blank and should be between 10 and 50 characters.
 * @param description A brief description of the exercise. Defaults to an empty string if null.
 * @param equipmentId The unique identifier of the equipment associated with the exercise. Must not be null.
 * @param muscleEngagements A set of muscle engagement details associated with the exercise. Must not be empty.
 */
public record ExerciseRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 4, max = 30, message = "{validation.size.message}")
        String name,
        String description,
        @JsonProperty("equipment_id")
        @NotNull(message = "{validation.not_null.message}")
        Long equipmentId,
        @Valid
        @JsonProperty("muscle_engagements")
        @NotEmpty(message = "{validation.not_empty.message}")
        Set<MuscleEngagementRequestDTO> muscleEngagements
) {

    /**
     * Constructor to initialize an ExerciseRequestDTO.
     *
     * @param id The unique identifier of the exercise. Can be null for new exercises.
     * @param name The name of the exercise. Must not be blank and should be between 10 and 50 characters.
     * @param description A brief description of the exercise. Defaults to an empty string if null.
     * @param equipmentId The unique identifier of the equipment associated with the exercise. Must not be null.
     * @param muscleEngagements A set of muscle engagement details associated with the exercise. Must not be empty.
     */
    public ExerciseRequestDTO(Long id, String name, String description, Long equipmentId, Set<MuscleEngagementRequestDTO> muscleEngagements) {
        this.id = id;
        this.name = name;
        this.description = description == null ? "" : description; // Ensure description defaults to an empty string if null
        this.equipmentId = equipmentId;
        this.muscleEngagements = muscleEngagements;
    }
}

