package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.config.ApplicationConfig;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.ExercisePaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.PaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.ExerciseResponseDTO;
import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApplicationConfig.API_VERSION + "/exercise")
@AllArgsConstructor
public class ExerciseController {

    private static final String EXERCISE_RESOURCE_PATH = ApplicationConfig.API_VERSION + "/exercise";

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
