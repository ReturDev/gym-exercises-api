package com.returdev.gym_exercises_api.service.data.exercise;

import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

/**
 * Service interface for managing exercises.
 *
 * <p>
 * This service provides methods to perform CRUD operations on exercise entities, including
 * retrieving, saving, updating, and deleting exercises.
 * </p>
 */
@Validated
public interface ExerciseService {

    /**
     * Retrieves an exercise entity by its ID.
     *
     * <p>
     * This method fetches the exercise entity associated with the given ID.
     * If the exercise does not exist, an exception is thrown.
     * </p>
     *
     * @param id the ID of the exercise to retrieve
     * @return the {@link ExerciseEntity} associated with the given ID
     */
    ExerciseEntity getExerciseById(Long id);

    /**
     * Retrieves all exercises in a paginated format.
     *
     * <p>
     * This method returns a page of exercise entities based on the provided pagination information.
     * </p>
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link ExerciseEntity} objects
     */
    Page<ExerciseEntity> getAllExercises(Pageable pageable);

    /**
     * Saves a new exercise entity to the database.
     *
     * <p>
     * This method persists the provided exercise entity and returns the saved entity.
     * The exercise entity must be valid according to the defined constraints.
     * </p>
     *
     * @param exercise the exercise entity to save
     * @return the saved {@link ExerciseEntity}
     */
    ExerciseEntity saveExercise(@Valid ExerciseEntity exercise);

    /**
     * Updates an existing exercise entity in the database.
     *
     * <p>
     * This method modifies the existing exercise entity with the provided information.
     * The exercise entity must be valid according to the defined constraints.
     * </p>
     *
     * @param exercise the exercise entity containing the updated information
     * @return the updated {@link ExerciseEntity}
     */
    ExerciseEntity updateExercise(@Valid ExerciseEntity exercise);

    /**
     * Partially updates an existing exercise entity in the database.
     *
     * <p>
     * This method updates only the fields that are present in the provided patch exercise entity.
     * The patch exercise entity must be valid according to the defined constraints.
     * </p>
     *
     * @param patchExercise the exercise entity containing the fields to update
     * @return the updated {@link ExerciseEntity}
     */
    ExerciseEntity partialUpdateExercise(@Valid ExerciseEntity patchExercise);

    /**
     * Deletes an exercise entity by its ID.
     *
     * <p>
     * This method removes the exercise associated with the given ID from the database.
     * If the exercise does not exist, an exception is thrown.
     * </p>
     *
     * @param id the ID of the exercise to delete
     */
    void deleteExercise(Long id);
}

