package com.returdev.gym_exercises_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for representing equipment details in the response.
 *
 * <p>
 * This record holds the information about a piece of equipment, including its
 * unique identifier and name. This is used for transferring equipment data
 * between layers of the application, such as from the service layer to the
 * presentation layer.
 * </p>
 *
 * @param id The unique identifier for the equipment.
 * @param name The name of the equipment.
 */
@Schema(name = "Equipment")
public record EquipmentResponseDTO(
        Long id,
        String name
) {}

