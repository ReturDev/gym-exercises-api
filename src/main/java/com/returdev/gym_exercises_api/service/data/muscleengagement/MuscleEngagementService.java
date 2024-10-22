package com.returdev.gym_exercises_api.service.data.muscleengagement;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing muscle engagements.
 */
public interface MuscleEngagementService {

    /**
     * Retrieves the ID of a given muscle engagement.
     *
     * <p>
     * This method checks if the specified muscle engagement exists and returns its ID if found.
     * </p>
     *
     * @param muscleEngagement the muscle engagement entity to retrieve the ID for
     * @return an {@link Optional} containing the ID of the muscle engagement if it exists, or empty if not
     */
    Optional<Long> getMuscleEngagementId(MuscleEngagementEntity muscleEngagement);

    /**
     * Saves a muscle engagement entity to the database.
     *
     * <p>
     * This method persists the provided muscle engagement entity and returns the saved entity.
     * </p>
     *
     * @param muscleEngagement the muscle engagement entity to save
     * @return the saved muscle engagement entity
     */
    MuscleEngagementEntity save(MuscleEngagementEntity muscleEngagement);

    /**
     * Retrieves a list of muscle engagement entities based on their IDs.
     *
     * <p>
     * This method takes a list of muscle engagement entities and returns a list containing
     * only those that have valid IDs.
     * </p>
     *
     * @param muscleEngagements the list of muscle engagement entities to retrieve
     * @return a list of muscle engagement entities with their IDs
     */
    List<MuscleEngagementEntity> getMuscleEngagementsWithId(List<MuscleEngagementEntity> muscleEngagements);
}

