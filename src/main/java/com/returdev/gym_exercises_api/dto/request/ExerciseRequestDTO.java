package com.returdev.gym_exercises_api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ExerciseRequestDTO(
        Long id,
        @NotBlank String name,
        String description,
        @NotNull Integer equipmentId,
        @Valid  @NotEmpty Set<MuscleEngagementRequestDTO> muscleEngagements
) {

    public ExerciseRequestDTO(Long id, @NotBlank String name, String description, @NotNull Integer equipmentId, @Valid @NotEmpty Set<MuscleEngagementRequestDTO> muscleEngagements) {
        this.id = id;
        this.name = name;
        this.description = description == null ? "" : description;
        this.equipmentId = equipmentId;
        this.muscleEngagements = muscleEngagements;
    }
}
