package com.returdev.gym_exercises_api.dto.response;

import java.util.List;

public record ExerciseResponseDTO(
    Long id,
    String name,
    String description,
    EquipmentResponseDTO equipment,
    List<MuscleEngagementResponseDTO> muscleEngagements
) {}
