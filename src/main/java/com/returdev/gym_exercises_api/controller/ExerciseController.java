package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.annotation.swagger.request.EquipmentPaginationRequestParameters;
import com.returdev.gym_exercises_api.annotation.swagger.response.*;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.ExercisePaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.ExerciseResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.PaginationResponseDTO;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.service.data.exercise.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * REST controller for managing exercises.
 *
 * <p>
 * This controller provides endpoints to create, read, update, and delete exercises.
 * It requires authentication for all actions and specific roles for modifying exercises.
 * </p>
 */
@RestController
@RequestMapping("v1/exercise")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "Exercise", description = "Endpoint to get exercises")
@InternalServerErrorResponseCode
@UnauthorizedResponseCode
@BadRequestResponseCode
public class ExerciseController {

    private static final String EXERCISE_RESOURCE_PATH = "v1/exercise";

    private final ExerciseService exerciseService;
    private final EntityDtoMapper mapper;

    /**
     * Retrieves a paginated list of exercises.
     *
     * @param pagination The pagination parameters.
     * @return A response entity containing a paginated list of exercises.
     */
    @Operation(
            summary = "Retrieve paginated list of exercises",
            description = "Fetches a paginated list of exercises entities from the system. This endpoint requires a **public key** for access."
    )
    @OkResponseCode
    @EquipmentPaginationRequestParameters
    @GetMapping()
    public ResponseEntity<PaginationResponseDTO<ExerciseResponseDTO>> getExercises(
            @Parameter(hidden = true)
            @Valid ExercisePaginationRequestDTO pagination
    ) {
        // Fetch paginated exercise entities from the service
        Page<ExerciseEntity> page = exerciseService.getAllExercises(
                mapper.paginationRequestDtoToPageable(pagination)
        );

        // Map the exercise entities to response DTOs and return them
        return ResponseEntity.ok(
                mapper.exerciseEntityToContentResponse(page)
        );
    }

    /**
     * Retrieves an exercise by its ID.
     *
     * @param id The ID of the exercise to retrieve.
     * @return A response entity containing the exercise details.
     */
    @Operation(
            summary = "Retrieve an exercises by ID",
            description = "Fetches the exercises entity with the specified ID. This endpoint requires a **public key** for access."
    )
    @OkResponseCode
    @NotFoundResponseCode
    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> getExerciseById(
            @PathVariable("id") Long id
    ) {
        // Fetch the exercise entity by ID and map it to response DTO
        return ResponseEntity.ok(
                mapper.exerciseEntityToContentResponse(
                        exerciseService.getExerciseById(id)
                )
        );
    }

    /**
     * Creates a new exercise.
     *
     * @param exerciseRequestDTO The exercise data to create.
     * @return A response entity containing the created exercise details.
     */
    @Operation(
            summary = "Create a new exercise",
            description = """
        Creates a new exercise entity in the system. This endpoint requires a **private key** for access.
        
        **Valid Muscles**:
        - UPPER_CHEST, LOWER_CHEST, INNER_CHEST
        - DORSALS, WIDE_BACK, RHOMBOIDS, TRAPEZES, LUMBAR
        - ANTERIOR_DELTOID, LATERAL_DELTOID, POSTERIOR_DELTOID
        - QUADRICEPS, HAMSTRINGS, CALVES, BUTTOCKS
        - BICEPS, TRICEPS, FOREARMS
        - ABS, OBLIQUES
        
        **Valid Muscle Activation Levels**:
        - HIGH, MEDIUM, LOW
        """
    )
    @CreatedResponseCode
    @NotFoundResponseCode
    @ConflictResponseCode
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(
                            ref = "#/components/schemas/SaveExercise"
                    )
            )
    )
    @PostMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> saveExercise(
            @RequestBody @Valid ExerciseRequestDTO exerciseRequestDTO
    ) {
        // Save the exercise entity and map it to response DTO
        ExerciseEntity exerciseSaved = exerciseService.saveExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );

        return ResponseEntity.created(
                createLocationURI(exerciseSaved.getId())
        ).body(
                mapper.exerciseEntityToContentResponse(exerciseSaved)
        );
    }

    /**
     * Updates an existing exercise.
     *
     * @param exerciseRequestDTO The updated exercise data.
     * @return A response entity containing the updated exercise details.
     */
    @Operation(
            summary = "Update an exercise",
            description = """
        Updates an existing exercise entity in the system. This endpoint requires a **private key** for access.
        
        **Valid Muscles**:
        - UPPER_CHEST, LOWER_CHEST, INNER_CHEST
        - DORSALS, WIDE_BACK, RHOMBOIDS, TRAPEZES, LUMBAR
        - ANTERIOR_DELTOID, LATERAL_DELTOID, POSTERIOR_DELTOID
        - QUADRICEPS, HAMSTRINGS, CALVES, BUTTOCKS
        - BICEPS, TRICEPS, FOREARMS
        - ABS, OBLIQUES
        
        **Valid Muscle Activation Levels**:
        - HIGH, MEDIUM, LOW
        """
    )
    @OkResponseCode
    @NotFoundResponseCode
    @ConflictResponseCode
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(
                            ref = "#/components/schemas/UpdateExercise"
                    )
            )
    )
    @PutMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> updateExercise(
            @RequestBody @Valid ExerciseRequestDTO exerciseRequestDTO
    ) {
        // Update the exercise entity and map it to response DTO
        ExerciseEntity exerciseUpdated = exerciseService.updateExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );

        return ResponseEntity.ok()
                .location(createLocationURI(exerciseUpdated.getId()))
                .body(
                        mapper.exerciseEntityToContentResponse(exerciseUpdated)
                );
    }

    /**
     * Partially updates an existing exercise.
     *
     * @param exerciseRequestDTO The exercise data to update.
     * @return A response entity containing the updated exercise details.
     */
    @Operation(
            summary = "Partially update an exercise",
            description = """
        Partially updates an existing exercise entity in the system. This endpoint requires a **private key** for access.
        
        **Valid Muscles**:
        - UPPER_CHEST, LOWER_CHEST, INNER_CHEST
        - DORSALS, WIDE_BACK, RHOMBOIDS, TRAPEZES, LUMBAR
        - ANTERIOR_DELTOID, LATERAL_DELTOID, POSTERIOR_DELTOID
        - QUADRICEPS, HAMSTRINGS, CALVES, BUTTOCKS
        - BICEPS, TRICEPS, FOREARMS
        - ABS, OBLIQUES
        
        **Valid Muscle Activation Levels**:
        - HIGH, MEDIUM, LOW
        """
    )
    @OkResponseCode
    @NotFoundResponseCode
    @ConflictResponseCode
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(
                            ref = "#/components/schemas/PatchExercise"
                    )
            )
    )
    @PatchMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<ExerciseResponseDTO>> patchExercise(
            @RequestBody ExerciseRequestDTO exerciseRequestDTO
    ) {
        // Partially update the exercise entity and map it to response DTO
        ExerciseEntity exerciseUpdated = exerciseService.partialUpdateExercise(
                mapper.exerciseRequestDtoToEntity(exerciseRequestDTO)
        );

        return ResponseEntity.ok()
                .location(createLocationURI(exerciseUpdated.getId()))
                .body(
                        mapper.exerciseEntityToContentResponse(exerciseUpdated)
                );
    }


    /**
     * Deletes an exercise by its ID.
     *
     * @param id The ID of the exercise to delete.
     * @return A response entity with no content.
     */
    @Operation(
            summary = "Delete an exercise",
            description = "Removes the specified exercise entity from the system. This endpoint requires a **private key** for access."
    )
    @NotFoundResponseCode
    @NoContentResponseCode
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable("id") Long id
    ) {
        // Delete the exercise by ID
        exerciseService.deleteExercise(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Creates a URI for the given exercise ID.
     *
     * @param id The ID of the exercise.
     * @return The URI of the created exercise resource.
     */
    private URI createLocationURI(Long id) {
        return URI.create(
                EXERCISE_RESOURCE_PATH + "/" + id
        );
    }

}

