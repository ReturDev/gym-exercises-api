package com.returdev.gym_exercises_api.dto.response;

public record MuscleEngagementResponseDTO(
        Long id,
        String muscle,
        String muscleActivationLevel
) {}
