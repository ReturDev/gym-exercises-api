package com.returdev.gym_exercises_api.dto.request;

import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import jakarta.validation.constraints.NotNull;

/**
 * A Data Transfer Object (DTO) representing a request to create or update a muscle engagement.
 *
 * <p>
 * This record is used to encapsulate the necessary information required
 * to represent a muscle engagement in an API request. It includes
 * validation annotations to ensure that the provided values are not null,
 * enforcing data integrity when processing requests related to muscle engagements.
 * </p>
 *
 * @param muscle The muscle associated with the engagement. Must not be null.
 * @param muscleActivationLevel The activation level of the muscle. Must not be null.
 */
public record MuscleEngagementRequestDTO(
        @NotNull(message = "{validation.not_null.message}") Muscle muscle,
        @NotNull(message = "{validation.not_null.message}") MuscleActivationLevel muscleActivationLevel
) {}

