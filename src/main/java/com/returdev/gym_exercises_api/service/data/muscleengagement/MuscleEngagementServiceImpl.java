package com.returdev.gym_exercises_api.service.data.muscleengagement;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.repositories.data.MuscleEngagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link MuscleEngagementService} for managing muscle engagements.
 *
 * <p>
 * This service interacts with the {@link MuscleEngagementRepository} to perform operations
 * related to muscle engagements, including retrieving IDs and saving entities.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class MuscleEngagementServiceImpl implements MuscleEngagementService {

    private final MuscleEngagementRepository muscleEngagementRepository;

    /**
     * Retrieves the ID of a muscle engagement based on muscle and activation level.
     *
     * <p>
     * This method uses the repository to find the ID of the specified muscle engagement entity.
     * If the entity exists, the ID is returned wrapped in an {@link Optional}. If not, an empty
     * {@link Optional} is returned.
     * </p>
     *
     * @param muscleEngagement the muscle engagement entity containing the muscle and activation level
     * @return an {@link Optional} containing the ID of the muscle engagement if found, otherwise empty
     */
    @Override
    public Optional<Long> getMuscleEngagementId(MuscleEngagementEntity muscleEngagement) {
        return muscleEngagementRepository.findIdByMuscleAndActivationLevel(muscleEngagement.getMuscle(), muscleEngagement.getMuscleActivationLevel());
    }

    /**
     * Saves a muscle engagement entity to the database.
     *
     * <p>
     * This method persists the provided muscle engagement entity using the repository and
     * returns the saved entity.
     * </p>
     *
     * @param muscleEngagement the muscle engagement entity to save
     * @return the saved muscle engagement entity
     */
    @Override
    public MuscleEngagementEntity save(MuscleEngagementEntity muscleEngagement) {
        return muscleEngagementRepository.save(muscleEngagement);
    }

    /**
     * Retrieves a list of muscle engagement entities based on the provided muscles and activation levels.
     *
     * <p>
     * This method uses the repository to find and return muscle engagement entities that match
     * the provided lists of muscles and activation levels.
     * </p>
     *
     * @param muscleEngagements the list of muscle engagement entities to retrieve
     * @return a list of muscle engagement entities that match the provided muscles and activation levels
     */
    @Override
    public List<MuscleEngagementEntity> getMuscleEngagementsWithId(List<MuscleEngagementEntity> muscleEngagements) {
        return muscleEngagementRepository.findByMusclesAndActivationLevels(
                muscleEngagements.stream().map(MuscleEngagementEntity::getMuscle).toList(),
                muscleEngagements.stream().map(MuscleEngagementEntity::getMuscleActivationLevel).toList()
        );
    }
}

