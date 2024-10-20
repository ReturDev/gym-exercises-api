package com.returdev.gym_exercises_api.dto.request;

import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import jakarta.validation.constraints.NotNull;

public record MuscleEngagementRequestDTO(
        @NotNull(message = "{validation.not_null.message}") Muscle muscle,
        @NotNull(message = "{validation.not_null.message}") MuscleActivationLevel muscleActivationLevel
) {}
