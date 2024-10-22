package com.returdev.gym_exercises_api.service.data.exercise;

import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface ExerciseService {

    ExerciseEntity getExerciseById(Long id);

    Page<ExerciseEntity> getAllExercises(Pageable pageable);

    ExerciseEntity saveExercise(@Valid ExerciseEntity exercise);

    ExerciseEntity updateExercise(@Valid ExerciseEntity exercise);

    ExerciseEntity partialUpdateExercise(@Valid ExerciseEntity patchExercise);

    void deleteExercise(Long id);

}
