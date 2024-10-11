package com.returdev.gym_exercises_api.dto.request;

import com.returdev.gym_exercises_api.enums.Muscle;
import com.returdev.gym_exercises_api.enums.MuscleActivationLevel;
import jakarta.validation.constraints.NotNull;

public record MuscleEngagementRequestDTO(
        @NotNull Muscle muscle,
        @NotNull MuscleActivationLevel muscleActivationLevel
) {}
