package com.returdev.gym_exercises_api.repositories.data;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link MuscleEngagementEntity} that provides database access operations.
 *
 * <p>
 * This repository extends {@link JpaRepository}, allowing for basic CRUD operations
 * and defining custom queries for retrieving muscle engagement data based on muscle and activation levels.
 * </p>
 */
@Repository
public interface MuscleEngagementRepository extends JpaRepository<MuscleEngagementEntity, Long> {

    /**
     * Finds a list of muscle engagement entities associated with a specific muscle.
     *
     * @param muscle the muscle for which to find muscle engagements
     * @return a list of {@link MuscleEngagementEntity} that engage the specified muscle
     */
    List<MuscleEngagementEntity> findByMuscle(Muscle muscle);

    /**
     * Finds a list of muscle engagement entities that match a specific muscle activation level.
     *
     * @param activationLevel the muscle activation level to filter by
     * @return a list of {@link MuscleEngagementEntity} that match the specified activation level
     */
    List<MuscleEngagementEntity> findByMuscleActivationLevel(MuscleActivationLevel activationLevel);

    /**
     * Finds the ID of a muscle engagement entity that matches a specific muscle and activation level.
     *
     * <p>
     * This query returns an {@link Optional} containing the ID of the muscle engagement
     * if found, or an empty {@link Optional} if not.
     * </p>
     *
     * @param muscle the muscle associated with the muscle engagement
     * @param activationLevel the activation level of the muscle engagement
     * @return an {@link Optional} containing the ID of the muscle engagement if found
     */
    @Query("SELECT me.id FROM MuscleEngagementEntity me WHERE me.muscle = :muscle AND me.muscleActivationLevel = :activationLevel")
    Optional<Long> findIdByMuscleAndActivationLevel(@Param("muscle") Muscle muscle, @Param("activationLevel") MuscleActivationLevel activationLevel);

    /**
     * Finds a  muscle engagement entity based on a muscle and activation level.
     *
     * @param muscle the list of muscles to filter by
     * @param activationLevel the list of activation levels to filter by
     * @return a list of {@link MuscleEngagementEntity} that match the specified muscles and activation levels
     */
    @Query("SELECT me FROM MuscleEngagementEntity me WHERE me.muscle = :muscle AND me.muscleActivationLevel = :activationLevel")
    MuscleEngagementEntity findByMuscleAndActivationLevel(@Param("muscle") Muscle muscle, @Param("activationLevel") MuscleActivationLevel activationLevel);

}

