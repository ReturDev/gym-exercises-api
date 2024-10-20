package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.ExercisePaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.ExerciseResponseDTO;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.service.data.exercise.ExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/exercise")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ExerciseController {

    private static final String EXERCISE_RESOURCE_PATH = "v1/exercise";

    private final ExerciseService exerciseService;
    private final EntityDtoMapper mapper;

    @GetMapping()
    public ResponseEntity<ContentResponseDTO<List<ExerciseResponseDTO>>> getExercises(@Valid ExercisePaginationRequestDTO pagination) {

        Page<ExerciseEntity> page = exerciseService.getAllExercises(
                mapper.paginationRequestDtoToPageable(pagination)
        );

        return ResponseEntity.ok(
                mapper.exerciseEntityToContentResponse(page)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> getExerciseById(
            @PathVariable("id") Long id
    ) {
        return exerciseService.getExerciseById(id).map(exercise ->
                ResponseEntity.ok(
                        mapper.exerciseEntityToContentResponse(exercise)
                )
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> saveExercise(
            @RequestBody @Valid ExerciseRequestDTO exerciseRequestDTO
    ) {

        ExerciseEntity exerciseSaved = exerciseService.saveExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );


        return ResponseEntity.created(
                createLocationURI(exerciseSaved.getId())
        ).body(
                mapper.exerciseEntityToContentResponse(exerciseSaved)
        );

    }

    @PutMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> updateExercise(
            @RequestBody @Valid ExerciseRequestDTO exerciseRequestDTO
    ) {

        ExerciseEntity exerciseUpdated = exerciseService.updateExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );

        return ResponseEntity.ok()
                .location(createLocationURI(exerciseUpdated.getId()))
                .body(
                        mapper.exerciseEntityToContentResponse(exerciseUpdated)
                );

    }

    @PatchMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> patchExercise(
            @RequestBody ExerciseRequestDTO exerciseRequestDTO
    ) {

        ExerciseEntity exerciseUpdated = exerciseService.partialUpdateExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );

        return ResponseEntity.ok()
                .location(createLocationURI(exerciseUpdated.getId()))
                .body(
                        mapper.exerciseEntityToContentResponse(exerciseUpdated)
                );

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable("id") Long id
    ) {

        exerciseService.deleteExercise(id);

        return ResponseEntity.noContent().build();

    }

    private URI createLocationURI(Long id) {
        return URI.create(
                EXERCISE_RESOURCE_PATH + "/" + id
        );
    }

}
