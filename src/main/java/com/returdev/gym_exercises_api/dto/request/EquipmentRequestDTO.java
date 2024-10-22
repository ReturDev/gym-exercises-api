package com.returdev.gym_exercises_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A Data Transfer Object (DTO) representing a request to create or update an equipment item.
 *
 * <p>
 * This record is used to encapsulate the necessary information required
 * for creating or updating an equipment item in an API request. It includes
 * validation annotations to ensure that the provided values adhere to
 * specific constraints, ensuring data integrity when processing equipment
 * related requests.
 * </p>
 *
 * @param id The unique identifier of the equipment item. Can be null for new equipment.
 * @param name The name of the equipment item. Must not be blank and should be between 3 and 25 characters.
 */
public record EquipmentRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 25, message = "{validation.size.message}")
        String name
) {}

