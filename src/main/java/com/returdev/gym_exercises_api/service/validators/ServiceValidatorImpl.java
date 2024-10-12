package com.returdev.gym_exercises_api.service.validators;

import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.util.message.MessageManager;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
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

    public ServiceValidatorImpl(MessageManager messageManager) {
        this.messageManager = messageManager;
    }


    @Override
    public void validateGetExerciseById(Long id) {
        validateIdIsNotNull(id);
    }

    @Override
    public void validateGetEquipmentById(Long id) {
        validateIdIsNotNull(id);
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

        notExistsById(
                equipment.getId(),
                existsById,
                EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE

        );

        equipmentExistsByName(equipment.getName(), existsByName);

    }

    @Override
    public void validateDeleteEquipment(Long id, Predicate<Long> existsById) {

        validateIdIsNotNull(id);

        notExistsById(
                id,
                existsById,
                EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE

        );

    }

    @Override
    public ExerciseEntity validateSaveExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById,
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
            Function<Long, Optional<EquipmentEntity>> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        validateIdIsNotNull(
                EXERCISE_UPDATE_ID_NULL_RESOURCE,
                exercise.getId()
        );

        notExistsById(
                exercise.getId(),
                existsById,
                EXERCISE_NOT_EXISTS_BY_ID_RESOURCE
        );

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
            Function<Long, Optional<EquipmentEntity>> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        validateIdIsNotNull(
                EXERCISE_UPDATE_ID_NULL_RESOURCE,
                exercise.getId()
        );

        ExerciseEntity persistedExercise = getExerciseById.apply(exercise.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        messageManager.getMessageWithParams(
                                EXERCISE_NOT_EXISTS_BY_ID_RESOURCE,
                                new Long[]{exercise.getId()}
                        )
                ));

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

        notExistsById(
                id,
                existsById,
                EXERCISE_NOT_EXISTS_BY_ID_RESOURCE
        );

    }

    private void validateIdIsNotNull(String errorMsgResourceKey, Long id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    messageManager.getMessage(errorMsgResourceKey)
            );
        }
    }

    private void validateIdIsNull(String msg, Long id) {
        if (id != null) {
            throw new IllegalArgumentException(
                    messageManager.getMessage(msg)
            );
        }
    }

    public void validateIdIsNotNull(Long id) {

        validateIdIsNotNull(GENERIC_ID_NULL_RESOURCE, id);

    }

    private ExerciseEntity validateSaveOrUpdateExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWitId
    ) {

        exerciseExistsByNameAndEquipmentId(exercise.getName(), exercise.getEquipment().getId(), existsByNameAndEquipmentId);

        EquipmentEntity equipment = getEquipmentById.apply(exercise.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        messageManager.getMessageWithParams(
                                EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE,
                                new Long[]{exercise.getEquipment().getId()}
                        )
                ));

        List<MuscleEngagementEntity> muscleEngagementEntities = getMuscleEngagementsWitId.apply(exercise.getMusclesEngagement());

        return new ExerciseEntity(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                equipment,
                muscleEngagementEntities

        );

    }

    private ExerciseEntity mergeExerciseFields(
            ExerciseEntity persistedExercise,
            ExerciseEntity partialUpdateExercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    ) {

        String updatedName = Optional.ofNullable(partialUpdateExercise.getName())
                .filter(it -> !it.isBlank())
                .orElse(persistedExercise.getName());

        String updatedDescription = Optional.ofNullable(partialUpdateExercise.getDescription()).filter(it -> !it.isBlank())
                .orElse(persistedExercise.getDescription());

        EquipmentEntity updatedEquipment = Optional.ofNullable(partialUpdateExercise.getEquipment())
                .map(equipment -> getEquipmentById.apply(equipment.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                messageManager.getMessageWithParams(
                                        EQUIPMENT_NOT_EXISTS_BY_ID_RESOURCE,
                                        new Long[]{equipment.getId()}
                                )
                        ))
                )
                .orElse(persistedExercise.getEquipment());


        List<MuscleEngagementEntity> updatedMuscleEngagements = Optional.ofNullable(partialUpdateExercise.getMusclesEngagement())
                .filter(it -> !it.isEmpty()).orElse(persistedExercise.getMusclesEngagement());

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

}
