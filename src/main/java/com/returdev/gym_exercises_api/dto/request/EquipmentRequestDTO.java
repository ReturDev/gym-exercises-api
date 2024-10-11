package com.returdev.gym_exercises_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EquipmentRequestDTO(
        Long id,
        @NotBlank String name
) {}
