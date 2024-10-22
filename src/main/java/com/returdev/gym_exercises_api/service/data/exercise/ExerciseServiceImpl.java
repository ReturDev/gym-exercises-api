package com.returdev.gym_exercises_api.service.data.exercise;

import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.repositories.data.ExerciseRepository;
import com.returdev.gym_exercises_api.service.data.equipment.EquipmentService;
import com.returdev.gym_exercises_api.service.data.muscleengagement.MuscleEngagementService;
import com.returdev.gym_exercises_api.service.data.validators.ServiceValidator;
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
    public ExerciseEntity getExerciseById(Long id) {

        return serviceValidator.validateGetExerciseById(
                id,
                exerciseRepository::findById
        );

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
