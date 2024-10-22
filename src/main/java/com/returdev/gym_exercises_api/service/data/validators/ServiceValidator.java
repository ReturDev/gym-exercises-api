package com.returdev.gym_exercises_api.service.data.validators;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ServiceValidator {

    ExerciseEntity validateGetExerciseById(
            Long id,
            Function<Long, Optional<ExerciseEntity>> getExerciseById
    );

    EquipmentEntity validateGetEquipmentById(
            Long id,
            Function<Long, Optional<EquipmentEntity>> getEquipmentById
    );

    void validateSaveEquipment(
            EquipmentEntity equipment,
            Predicate<String> existsByName
    );

    void validateUpdateEquipment(
            EquipmentEntity equipment,
            Predicate<Long> existsById,
            Predicate<String> existsByName
    );

    void validateDeleteEquipment(
            Long id,
            Predicate<Long> existsById
    );

    ExerciseEntity validateSaveExercise(
            ExerciseEntity exercise,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );


    ExerciseEntity validateUpdateExercise(
            ExerciseEntity exercise,
            Predicate<Long> existsById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );

    ExerciseEntity validatePartialUpdateExercise(
            ExerciseEntity exercise,
            Function<Long, Optional<ExerciseEntity>> getExerciseById,
            BiPredicate<String, Long> existsByNameAndEquipmentId,
            Function<Long, EquipmentEntity> getEquipmentById,
            Function<List<MuscleEngagementEntity>, List<MuscleEngagementEntity>> getMuscleEngagementsWithId
    );


    void validateDeleteExercise(Long id, Predicate<Long> existsById);
}
