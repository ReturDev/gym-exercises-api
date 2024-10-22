package com.returdev.gym_exercises_api.dto.response;

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
public record EquipmentResponseDTO(
        Long id,
        String name
) {}

