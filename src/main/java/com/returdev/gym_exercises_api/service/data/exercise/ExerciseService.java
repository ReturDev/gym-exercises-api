package com.returdev.gym_exercises_api.service.data.exercise;

import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExerciseService {

    Page<ExerciseEntity> getAllExercises(Pageable pageable);

    Optional<ExerciseEntity> getExerciseById(Long id);

    ExerciseEntity saveExercise(ExerciseEntity exercise);

    ExerciseEntity updateExercise(ExerciseEntity exercise);

    ExerciseEntity partialUpdateExercise(ExerciseEntity patchExercise);

    void deleteExercise(Long id);

}
