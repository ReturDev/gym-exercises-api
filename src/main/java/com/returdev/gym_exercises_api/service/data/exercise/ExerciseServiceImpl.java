package com.returdev.gym_exercises_api.service.data.exercise;

import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.repositories.data.ExerciseRepository;
import com.returdev.gym_exercises_api.service.data.equipment.EquipmentService;
import com.returdev.gym_exercises_api.service.data.muscleengagement.MuscleEngagementService;
import com.returdev.gym_exercises_api.service.data.validators.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link ExerciseService} interface.
 *
 * <p>
 * This service is responsible for managing exercise entities, including creating,
 * updating, partially updating, retrieving, and deleting exercises. It validates
 * the inputs using the {@link ServiceValidator} and interacts with the necessary
 * repositories and services such as {@link ExerciseRepository}, {@link EquipmentService},
 * and {@link MuscleEngagementService}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ServiceValidator serviceValidator;
    private final ExerciseRepository exerciseRepository;
    private final EquipmentService equipmentService;
    private final MuscleEngagementService muscleEngagementService;

    /**
     * Retrieves all exercises in a paginated format.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link ExerciseEntity} objects
     */
    @Override
    public Page<ExerciseEntity> getAllExercises(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }

    /**
     * Retrieves an exercise by its ID after validation.
     *
     * <p>
     * Validates that the ID is not null and exists, then returns the corresponding exercise.
     * </p>
     *
     * @param id the ID of the exercise to retrieve
     * @return the {@link ExerciseEntity} associated with the given ID
     */
    @Override
    public ExerciseEntity getExerciseById(Long id) {
        return serviceValidator.validateGetExerciseById(
                id,
                exerciseRepository::findById
        );
    }

    /**
     * Saves a new exercise entity after validation.
     *
     * <p>
     * Validates the input exercise entity, checking for conflicts (e.g., duplicate names)
     * and relationships (e.g., valid equipment and muscle engagements), then persists
     * the entity in the database.
     * </p>
     *
     * @param exercise the exercise entity to save
     * @return the saved {@link ExerciseEntity}
     */
    @Override
    public ExerciseEntity saveExercise(ExerciseEntity exercise) {
        ExerciseEntity validatedExercise = serviceValidator.validateSaveExercise(
                exercise,
                exerciseRepository::existsByNameAndEquipmentId,
                equipmentService::getEquipmentById,
                muscleEngagementService::getMuscleEngagementsWithId
        );

        return exerciseRepository.save(validatedExercise);
    }

    /**
     * Updates an existing exercise entity after validation.
     *
     * <p>
     * Validates the input exercise entity, ensuring that the entity exists,
     * and checks for potential conflicts or updates in related entities.
     * </p>
     *
     * @param exercise the exercise entity containing updated information
     * @return the updated {@link ExerciseEntity}
     */
    @Override
    public ExerciseEntity updateExercise(ExerciseEntity exercise) {
        ExerciseEntity validatedExercise = serviceValidator.validateUpdateExercise(
                exercise,
                exerciseRepository::existsById,
                exerciseRepository::existsByNameAndEquipmentId,
                equipmentService::getEquipmentById,
                muscleEngagementService::getMuscleEngagementsWithId
        );

        return exerciseRepository.save(validatedExercise);
    }

    /**
     * Partially updates an existing exercise entity after validation.
     *
     * <p>
     * Updates only the specified fields of the exercise entity, validating any
     * new information provided, and persists the updated entity in the database.
     * </p>
     *
     * @param exercise the exercise entity containing the fields to update
     * @return the updated {@link ExerciseEntity}
     */
    @Override
    public ExerciseEntity partialUpdateExercise(ExerciseEntity exercise) {
        ExerciseEntity validatedExercise = serviceValidator.validatePartialUpdateExercise(
                exercise,
                exerciseRepository::findById,
                exerciseRepository::existsByNameAndEquipmentId,
                equipmentService::getEquipmentById,
                muscleEngagementService::getMuscleEngagementsWithId
        );

        return exerciseRepository.save(validatedExercise);
    }

    /**
     * Deletes an exercise entity by its ID after validation.
     *
     * <p>
     * Validates that the ID exists before deleting the corresponding exercise entity
     * from the database.
     * </p>
     *
     * @param id the ID of the exercise to delete
     */
    @Override
    public void deleteExercise(Long id) {
        serviceValidator.validateDeleteExercise(
                id,
                exerciseRepository::existsById
        );

        exerciseRepository.deleteById(id);
    }

}

