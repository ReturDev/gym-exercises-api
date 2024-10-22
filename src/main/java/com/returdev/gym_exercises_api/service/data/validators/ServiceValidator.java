package com.returdev.gym_exercises_api.service.data.validators;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface for validating service operations related to exercises and equipment.
 *
 * <p>
 * This interface defines validation methods for managing exercises and equipment entities,
 * ensuring that the necessary conditions are met before performing operations like retrieving,
 * saving, updating, or deleting entities. Implementations should provide the specific validation
 * logic according to the application's requirements.
 * </p>
 */
public interface ServiceValidator {

    /**
     * Validates the retrieval of an exercise by its ID.
     *
     * @param id The ID of the exercise to retrieve.
     * @param getExerciseById A function that retrieves the exercise by its ID, returning an optional.
     * @return The validated {@link ExerciseEntity} if found.
     */
    ExerciseEntity validateGetExerciseById(
            Long id,
            Function<Long, Optional<ExerciseEntity>> getExerciseById
    );

    /**
     * Validates the retrieval of equipment by its ID.
     *
     * @param id The ID of the equipment to retrieve.
     * @param getEquipmentById A function that retrieves the equipment by its ID, returning an optional.
     * @return The validated {@link EquipmentEntity} if found.
     */
    EquipmentEntity validateGetEquipmentById(
            Long id,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById
    );

    /**
     * Validates the saving of a new equipment entity.
     *
     * @param equipment The equipment entity to save.
     * @param existsByName A predicate to check if the equipment name already exists.
     */
    void validateSaveEquipment(
            EquipmentEntity equipment,
            Predicate<String> existsByName
    );

    /**
     * Validates the updating of an existing equipment entity.
     *
     * @param equipment The equipment entity to update.
     * @param existsById A predicate to check if the equipment ID exists.
     * @param existsByName A predicate to check if the equipment name already exists.
     */
    void validateUpdateEquipment(
            EquipmentEntity equipment,
            Predicate<Long> existsById,
            Predicate<String> existsByName
    );

    /**
     * Validates the deletion of an equipment entity.
     *
     * @param id The ID of the equipment to delete.
     * @param existsById A predicate to check if the equipment ID exists.
     */
    void validateDeleteEquipment(
            Long id,
            Predicate<Long> existsById
    );

    /**
     * Validates the saving of a new exercise entity.
     *
     * @param exercise The exercise entity to save.
     * @param existsByNameAndEquipmentId A predicate to check if the exercise name and equipment ID combination exists.
     * @param getEquipmentById A function that retrieves the equipment by its ID.
     * @param getMuscleEngagementsWithId A function that retrieves muscle engagements by their IDs.
     * @return The validated {@link ExerciseEntity}.
     */
    ExerciseEntity validateSaveExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );

    /**
     * Validates the updating of an existing exercise entity.
     *
     * @param exercise The exercise entity to update.
     * @param existsById A predicate to check if the exercise ID exists.
     * @param existsByNameAndEquipmentId A predicate to check if the exercise name and equipment ID combination exists.
     * @param getEquipmentById A function that retrieves the equipment by its ID.
     * @param getMuscleEngagementsWithId A function that retrieves muscle engagements by their IDs.
     * @return The validated {@link ExerciseEntity}.
     */
    ExerciseEntity validateUpdateExercise(
            ExerciseEntity exercise,
            Predicate<Long> existsById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );

    /**
     * Validates the partial update of an existing exercise entity.
     *
     * @param exercise The exercise entity to update.
     * @param getExerciseById A function that retrieves the exercise by its ID.
     * @param existsByNameAndEquipmentId A predicate to check if the exercise name and equipment ID combination exists.
     * @param getEquipmentById A function that retrieves the equipment by its ID.
     * @param getMuscleEngagementsWithId A function that retrieves muscle engagements by their IDs.
     * @return The validated {@link ExerciseEntity}.
     */
    ExerciseEntity validatePartialUpdateExercise(
            ExerciseEntity exercise,
            Function<Long, Optional<ExerciseEntity>> getExerciseById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );

    /**
     * Validates the deletion of an exercise entity.
     *
     * @param id The ID of the exercise to delete.
     * @param existsById A predicate to check if the exercise ID exists.
     */
    void validateDeleteExercise(Long id, Predicate<Long> existsById);
}

