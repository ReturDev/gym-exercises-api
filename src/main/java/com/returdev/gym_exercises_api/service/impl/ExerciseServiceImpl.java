package com.returdev.gym_exercises_api.service.impl;

import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.repositories.ExerciseRepository;
import com.returdev.gym_exercises_api.service.EquipmentService;
import com.returdev.gym_exercises_api.service.ExerciseService;
import com.returdev.gym_exercises_api.service.MuscleEngagementService;
import com.returdev.gym_exercises_api.service.validators.ServiceValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ServiceValidator serviceValidator;

    private final ExerciseRepository exerciseRepository;
    private final EquipmentService equipmentService;
    private final MuscleEngagementService muscleEngagementService;

    public ExerciseServiceImpl(ServiceValidator serviceValidator, ExerciseRepository exerciseRepository, EquipmentService equipmentService, MuscleEngagementService muscleEngagementService) {
        this.serviceValidator = serviceValidator;
        this.exerciseRepository = exerciseRepository;
        this.equipmentService = equipmentService;
        this.muscleEngagementService = muscleEngagementService;
    }


    @Override
    public Page<ExerciseEntity> getAllExercises(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }

    @Override
    public Optional<ExerciseEntity> getExerciseById(Long id) {

        serviceValidator.validateGetExerciseById(id);

        return exerciseRepository.findById(id);

    }

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

    @Override
    public void deleteExercise(Long id) {

        serviceValidator.validateDeleteExercise(
                id,
                exerciseRepository::existsById
        );

        exerciseRepository.deleteById(id);

    }

}
