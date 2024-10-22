package com.returdev.gym_exercises_api.service.data.validators;

import com.returdev.gym_exercises_api.manager.message.MessageManager;
import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Implementation of the {@link ServiceValidator} interface for validating service operations
 * related to exercises and equipment.
 *
 * <p>
 * This class provides the logic for validating the existence and state of exercises and equipment
 * entities, ensuring that the appropriate conditions are met before performing operations like
 * saving, updating, or deleting. It utilizes a {@link MessageManager} to retrieve localized
 * error messages for validation exceptions.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ServiceValidatorImpl implements ServiceValidator {

    private static final String GENERIC_ID_NULL_RESOURCE = "exception.generic.id_is_null";
    private static final String EQUIPMENT_SAVE_ID_NOT_NULL_RESOURCE = "exception.equipment.save.id_is_not_null";
    private static final String EQUIPMENT_UPDATE_ID_NULL_RESOURCE = "exception.equipment.update.id_is_null";
    private static final String EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE = "exception.equipment.not_exists_by_id";
    private static final String EXERCISE_SAVE_ID_NOT_NULL_RESOURCE = "exception.exercise.save.id_is_not_null";
    private static final String EXERCISE_UPDATE_ID_NULL_RESOURCE = "exception.exercise.update.id_is_null";
    private static final String EXERCISE_NOT_EXISTS_BY_ID_RESOURCE = "exception.exercise.not_exists_by_id";
    private static final String EQUIPMENT_EXISTS_BY_NAME_RESOURCE = "exception.equipment.exists_by_name";
    private static final String EXERCISE_EXISTS_BY_NAME_AND_EQUIPMENT_ID_RESOURCE = "exception.exercise.exists_by_name_and_equipment_id";


    private final MessageManager messageManager;

    @Override
    public ExerciseEntity validateGetExerciseById(
            Long id,
            Function<Long, Optional<ExerciseEntity>> getExerciseById
    ) {

        validateIdIsNotNull(id);

        return getExerciseEntityById(id, getExerciseById);

    }

    @Override
    public EquipmentEntity validateGetEquipmentById(Long id, Function<Long, Optional<EquipmentEntity>> getEquipmentById) {

        validateIdIsNotNull(id);

        return getEquipmentEntityById(id, getEquipmentById);

    }


    @Override
    public void validateSaveEquipment(EquipmentEntity equipment, Predicate<String> existsByName) {

        validateIdIsNull(
                EQUIPMENT_SAVE_ID_NOT_NULL_RESOURCE,
                equipment.getId()
        );

        equipmentExistsByName(equipment.getName(), existsByName);

    }

    @Override
    public void validateUpdateEquipment(EquipmentEntity equipment, Predicate<Long> existsById, Predicate<String> existsByName) {

        validateIdIsNotNull(
                EQUIPMENT_UPDATE_ID_NULL_RESOURCE,
                equipment.getId()
        );

        equipmentNotExistsById(equipment.getId(), existsById);

        equipmentExistsByName(equipment.getName(), existsByName);

    }

    @Override
    public void validateDeleteEquipment(Long id, Predicate<Long> existsById) {

        validateIdIsNotNull(id);

        equipmentNotExistsById(id, existsById);

    }

    @Override
    public ExerciseEntity validateSaveExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        validateIdIsNull(
                EXERCISE_SAVE_ID_NOT_NULL_RESOURCE,
                exercise.getId()
        );

        return validateSaveOrUpdateExercise(
                exercise,
                existsByNameAndEquipmentId,
                getEquipmentById,
                getMuscleEngagementsWithId
        );

    }

    @Override
    public ExerciseEntity validateUpdateExercise(
            ExerciseEntity exercise,
            Predicate<Long> existsById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        validateIdIsNotNull(
                EXERCISE_UPDATE_ID_NULL_RESOURCE,
                exercise.getId()
        );

        exerciseNotExistsById(exercise.getId(), existsById);

        return validateSaveOrUpdateExercise(
                exercise,
                existsByNameAndEquipmentId,
                getEquipmentById,
                getMuscleEngagementsWithId
        );

    }

    @Override
    public ExerciseEntity validatePartialUpdateExercise(
            ExerciseEntity exercise,
            Function<Long, Optional<ExerciseEntity>> getExerciseById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        validateIdIsNotNull(
                EXERCISE_UPDATE_ID_NULL_RESOURCE,
                exercise.getId()
        );

        ExerciseEntity persistedExercise = getExerciseEntityById(exercise.getId(), getExerciseById);

        return mergeExerciseFields(
                persistedExercise,
                exercise,
                existsByNameAndEquipmentId,
                getEquipmentById,
                getMuscleEngagementsWithId
        );
    }

    @Override
    public void validateDeleteExercise(Long id, Predicate<Long> existsById) {

        validateIdIsNotNull(id);

        exerciseNotExistsById(id, existsById);

    }

    /**
     * Validates that the ID is not null.
     *
     * <p>
     * Throws an {@link IllegalArgumentException} if the ID is null, using the provided error message resource key.
     * </p>
     *
     * @param errorMsgResourceKey the resource key for the error message
     * @param id the ID to validate
     */
    private void validateIdIsNotNull(String errorMsgResourceKey, Long id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    messageManager.getMessage(errorMsgResourceKey)
            );
        }
    }

    /**
     * Validates that the ID is null.
     *
     * <p>
     * Throws an {@link IllegalArgumentException} if the ID is not null, using the provided error message resource key.
     * </p>
     *
     * @param msg the resource key for the error message
     * @param id the ID to validate
     */
    private void validateIdIsNull(String msg, Long id) {
        if (id != null) {
            throw new IllegalArgumentException(
                    messageManager.getMessage(msg)
            );
        }
    }

    /**
     * Validates that the ID is not null, using a generic error message resource key.
     *
     * @param id the ID to validate
     */
    public void validateIdIsNotNull(Long id) {

        validateIdIsNotNull(GENERIC_ID_NULL_RESOURCE, id);

    }

    /**
     * Validates the given exercise entity for saving or updating.
     *
     * <p>
     * Checks if the exercise name already exists for the specified equipment and
     * creates a new exercise entity with the validated fields.
     * </p>
     *
     * @param exercise the exercise entity to validate
     * @param existsByNameAndEquipmentId a predicate to check if an exercise name exists for the equipment ID
     * @param getEquipmentById a function to retrieve an equipment entity by ID
     * @param getMuscleEngagementsWithId a function to retrieve muscle engagements by ID
     * @return the validated exercise entity
     */
    private ExerciseEntity validateSaveOrUpdateExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        exerciseExistsByNameAndEquipmentId(exercise.getName(), exercise.getEquipment().getId(), existsByNameAndEquipmentId);

        EquipmentEntity equipment = getEquipmentById.apply(exercise.getEquipment().getId());

        List<MuscleEngagementEntity> muscleEngagementEntities = getMuscleEngagementsWithId.apply(exercise.getMusclesEngagement());

        return new ExerciseEntity(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                equipment,
                muscleEngagementEntities

        );

    }

    /**
     * Merges the fields of a persisted exercise entity with the fields of a partial update exercise entity.
     *
     * <p>
     * Validates that the updated exercise name does not already exist for the specified equipment before returning
     * the merged exercise entity.
     * </p>
     *
     * @param persistedExercise the existing exercise entity
     * @param partialUpdateExercise the partial update exercise entity
     * @param existsByNameAndEquipmentId a predicate to check if an exercise name exists for the equipment ID
     * @param getEquipmentById a function to retrieve an equipment entity by ID
     * @param getMuscleEngagementsWithId a function to retrieve muscle engagements by ID
     * @return the merged exercise entity
     */
    private ExerciseEntity mergeExerciseFields(
            ExerciseEntity persistedExercise,
            ExerciseEntity partialUpdateExercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        String updatedName = Optional.ofNullable(partialUpdateExercise.getName())
                .filter(it -> !it.isBlank())
                .orElse(persistedExercise.getName());

        String updatedDescription = Optional.ofNullable(partialUpdateExercise.getDescription()).filter(it -> !it.isBlank())
                .orElse(persistedExercise.getDescription());

        EquipmentEntity updatedEquipment = Optional.ofNullable(partialUpdateExercise.getEquipment())
                .map(equipment -> getEquipmentById.apply(equipment.getId()))
                .orElse(persistedExercise.getEquipment());


        List<MuscleEngagementEntity> updatedMuscleEngagements = getMuscleEngagementsWithId.apply(
                Optional.ofNullable(partialUpdateExercise.getMusclesEngagement())
                        .filter(it -> !it.isEmpty()).orElse(persistedExercise.getMusclesEngagement())
        );

        if (!updatedName.equals(persistedExercise.getName()) || !updatedEquipment.getId().equals(persistedExercise.getId())) {
            exerciseExistsByNameAndEquipmentId(updatedName, updatedEquipment.getId(), existsByNameAndEquipmentId);
        }

        return new ExerciseEntity(
                persistedExercise.getId(),
                updatedName,
                updatedDescription,
                updatedEquipment,
                updatedMuscleEngagements
        );

    }

    /**
     * Validates that the specified ID does not exist using the provided predicate.
     *
     * <p>
     * Throws an {@link EntityNotFoundException} if the ID exists, using the provided error message resource key.
     * </p>
     *
     * @param id the ID to validate
     * @param existsById a predicate to check if an entity exists by ID
     * @param errorMsgResourceKey the resource key for the error message
     */
    private void notExistsById(Long id, Predicate<Long> existsById, String errorMsgResourceKey) {
        if (!existsById.test(id)) {
            throw new EntityNotFoundException(
                    messageManager.getMessageWithParams(
                            errorMsgResourceKey,
                            new Long[]{id}
                    )
            );
        }
    }


    /**
     * Checks if equipment with the specified name already exists.
     *
     * <p>
     * Throws an {@link EntityExistsException} if the name exists, using the appropriate error message.
     * </p>
     *
     * @param name the name of the equipment to check
     * @param existsByName a predicate to check if an equipment name already exists
     */
    private void equipmentExistsByName(String name, Predicate<String> existsByName) {

        if (existsByName.test(name)) {
            throw new EntityExistsException(
                    messageManager.getMessageWithParams(
                            EQUIPMENT_EXISTS_BY_NAME_RESOURCE,
                            new String[]{name}
                    )
            );
        }

    }

    /**
     * Checks if an exercise with the specified name and equipment ID already exists.
     *
     * <p>
     * Throws an {@link EntityExistsException} if the exercise exists, using the appropriate error message.
     * </p>
     *
     * @param equipmentName the name of the exercise to check
     * @param equipmentId the ID of the equipment associated with the exercise
     * @param existsByNameAndEquipmentId a predicate to check if an exercise name exists for the equipment ID
     */
    private void exerciseExistsByNameAndEquipmentId(
            String equipmentName,
            Long equipmentId,
            BiPredicate<String, Long> existsByNameAndEquipmentId
    ) {

        if (existsByNameAndEquipmentId.test(equipmentName, equipmentId)) {
            throw new EntityExistsException(
                    messageManager.getMessageWithParams(
                            EXERCISE_EXISTS_BY_NAME_AND_EQUIPMENT_ID_RESOURCE,
                            new Object[]{equipmentName, equipmentId}
                    )
            );
        }

    }

    /**
     * Validates that the exercise with the specified ID does not exist.
     *
     * <p>
     * Throws an {@link EntityNotFoundException} if the exercise exists, using the appropriate error message.
     * </p>
     *
     * @param id the ID of the exercise to validate
     * @param existsById a predicate to check if an exercise exists by ID
     */
    private void exerciseNotExistsById(
            Long id,
            Predicate<Long> existsById
    ) {
        notExistsById(
                id,
                existsById,
                EXERCISE_NOT_EXISTS_BY_ID_RESOURCE
        );
    }


    /**
     * Retrieves an exercise entity by ID, throwing an exception if it does not exist.
     *
     * <p>
     * This method is typically used to ensure the exercise exists before performing operations on it.
     * </p>
     *
     * @param id the ID of the exercise to retrieve
     * @param getExerciseById a function to retrieve an exercise entity by ID
     * @return the retrieved exercise entity
     */
    private ExerciseEntity getExerciseEntityById(
            Long id,
            Function<Long, Optional<ExerciseEntity>> getExerciseById
    ) {

        return getExerciseById.apply(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageManager.getMessageWithParams(
                                EXERCISE_NOT_EXISTS_BY_ID_RESOURCE,
                                new Long[]{id}
                        )
                ));

    }

    /**
     * Validates that the equipment with the specified ID does not exist.
     *
     * <p>
     * Throws an {@link EntityNotFoundException} if the equipment exists, using the appropriate error message.
     * </p>
     *
     * @param id the ID of the equipment to validate
     * @param existsById a predicate to check if equipment exists by ID
     */
    private void equipmentNotExistsById(
            Long id,
            Predicate<Long> existsById
    ) {
        notExistsById(
                id,
                existsById,
                EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE
        );
    }


    /**
     * Retrieves an equipment entity by ID, throwing an exception if it does not exist.
     *
     * <p>
     * This method is typically used to ensure the equipment exists before performing operations on it.
     * </p>
     *
     * @param id the ID of the equipment to retrieve
     * @param getEquipmentById a function to retrieve an equipment entity by ID
     * @return the retrieved equipment entity
     */
    private EquipmentEntity getEquipmentEntityById(
            Long id,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById
    ) {
        return getEquipmentById.apply(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageManager.getMessageWithParams(
                                EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE,
                                new Long[]{id}
                        )
                ));

    }


}
