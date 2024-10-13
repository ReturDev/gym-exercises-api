package com.returdev.gym_exercises_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipmentRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 25, message = "{validation.size.message}")
        String name
) {}
