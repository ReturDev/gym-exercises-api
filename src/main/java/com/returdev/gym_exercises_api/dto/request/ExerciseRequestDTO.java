package com.returdev.gym_exercises_api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ExerciseRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}") String name,
        String description,
        @NotNull(message = "{validation.not_null.message}") Long equipmentId,
        @Valid
        @NotEmpty(message = "{validation.not_empty.message}") Set<MuscleEngagementRequestDTO> muscleEngagements
) {

    public ExerciseRequestDTO(Long id, String name, String description, Long equipmentId, Set<MuscleEngagementRequestDTO> muscleEngagements) {
        this.id = id;
        this.name = name;
        this.description = description == null ? "" : description;
        this.equipmentId = equipmentId;
        this.muscleEngagements = muscleEngagements;
    }
}
