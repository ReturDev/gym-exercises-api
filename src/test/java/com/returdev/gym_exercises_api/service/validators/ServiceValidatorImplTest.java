package com.returdev.gym_exercises_api.service.validators;

import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ServiceValidatorImplTest {

    @Autowired
    public ServiceValidator serviceValidator;

    // ----------------------------------------
    //          TESTS FOR GET EXERCISE
    // ----------------------------------------

    @Test
    public void validateGetExerciseById_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateGetExerciseById(null)
        );
    }

    @Test
    public void validateGetExerciseById_whenIdIsNotNull_shouldNotThrowException() {
        assertDoesNotThrow(() -> serviceValidator.validateGetExerciseById(1L));
    }

    // ----------------------------------------
    //          TESTS FOR GET EQUIPMENT
    // ----------------------------------------

    @Test
    public void validateGetEquipmentById_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateGetEquipmentById(null)
        );
    }

    @Test
    public void validateGetEquipmentById_whenIdIsNotNull_shouldNotThrowException() {
        assertDoesNotThrow(() -> serviceValidator.validateGetEquipmentById(1L));
    }

    // ----------------------------------------
    //         TESTS FOR SAVE EQUIPMENT
    // ----------------------------------------

    @Test
    public void validateSaveEquipment_whenIdIsNotNull_shouldThrowIllegalArgumentException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(1L, null);
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateSaveEquipment(equipmentEntity, null)
        );
    }

    @Test
    public void validateSaveEquipment_whenNameExists_shouldThrowEntityExistsException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(null, "name");
        assertThrows(EntityExistsException.class,
                () -> serviceValidator.validateSaveEquipment(equipmentEntity, equipmentName -> true)
        );
    }

    @Test
    public void validateSaveEquipment_whenIdIsNullAndNameIsUnique_shouldNotThrowException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(null, "uniqueName");
        assertDoesNotThrow(() -> serviceValidator.validateSaveEquipment(equipmentEntity, equipmentName -> false));
    }

    // ----------------------------------------
    //         TESTS FOR UPDATE EQUIPMENT
    // ----------------------------------------

    @Test
    public void validateUpdateEquipment_whenIdIsNull_shouldThrowIllegalArgumentException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateUpdateEquipment(equipmentEntity, null, null)
        );
    }

    @Test
    public void validateUpdateEquipment_whenEquipmentNotExistsById_shouldThrowEntityNotFoundException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(1L, "");
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateUpdateEquipment(equipmentEntity, id -> false, null)
        );
    }

    @Test
    public void validateUpdateEquipment_whenNameExists_shouldThrowEntityExistsException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(1L, "");
        assertThrows(EntityExistsException.class,
                () -> serviceValidator.validateUpdateEquipment(equipmentEntity, id -> true, name -> true)
        );
    }

    @Test
    public void validateUpdateEquipment_whenIdExistsAndNameIsUnique_shouldNotThrowException() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(1L, "");
        assertDoesNotThrow(() -> serviceValidator.validateUpdateEquipment(equipmentEntity, id -> true, name -> false));
    }

    // ----------------------------------------
    //         TESTS FOR DELETE EQUIPMENT
    // ----------------------------------------

    @Test
    public void validateDeleteEquipment_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateDeleteEquipment(null, null)
        );
    }

    @Test
    public void validateDeleteEquipment_whenNotExistsById_shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateDeleteEquipment(1L, id -> false)
        );
    }

    @Test
    public void validateDeleteEquipment_whenExistsById_shouldNotThrowException() {
        assertDoesNotThrow(() -> serviceValidator.validateDeleteEquipment(1L, id -> true));
    }

    // ----------------------------------------
    //        TESTS FOR SAVE EXERCISE
    // ----------------------------------------

    @Test
    public void validateSaveExercise_whenIdIsNotNull_shouldThrowIllegalArgumentException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, null, null);
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateSaveExercise(exerciseEntity, null, null, null)
        );
    }

    @Test
    public void validateSaveExercise_whenExerciseExistsByNameAndEquipmentId_shouldThrowEntityExistsException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(null, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityExistsException.class,
                () -> serviceValidator.validateSaveExercise(
                        exerciseEntity,
                        (name, id) -> true,
                        id -> Optional.of(new EquipmentEntity()),
                        null
                )
        );
    }

    @Test
    public void validateSaveExercise_whenEquipmentNotExistsById_shouldThrowEntityNotFoundException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(null, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateSaveExercise(
                        exerciseEntity,
                        (name, id) -> false,
                        id -> Optional.empty(),
                        null
                )
        );
    }

    @Test
    public void validateSaveExercise_whenAllParametersAreValid_shouldNotThrowException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(null, null, null, new EquipmentEntity(1L, null), null);
        assertDoesNotThrow(() -> serviceValidator.validateSaveExercise(
                exerciseEntity,
                (name, id) -> false,
                id -> Optional.of(new EquipmentEntity()),
                muscleEngagementEntities -> List.of()
        ));
    }

    // ----------------------------------------
    //        TESTS FOR UPDATE EXERCISE
    // ----------------------------------------

    @Test
    public void validateUpdateExercise_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateUpdateExercise(new ExerciseEntity(), null, null, null, null)
        );
    }

    @Test
    public void validateUpdateExercise_whenExerciseNotExistsById_shouldThrowEntityNotFoundException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateUpdateExercise(exerciseEntity, id -> false, null, null, null)
        );
    }

    @Test
    public void validateUpdateExercise_whenExerciseExistsByNameAndEquipmentId_shouldThrowEntityExistsException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityExistsException.class,
                () -> serviceValidator.validateUpdateExercise(
                        exerciseEntity,
                        id -> true,
                        (name, id) -> true,
                        null,
                        null
                )
        );
    }

    @Test
    public void validateUpdateExercise_whenAllParametersAreValid_shouldNotThrowException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, new EquipmentEntity(1L, null), null);
        assertDoesNotThrow(() -> serviceValidator.validateUpdateExercise(
                exerciseEntity,
                id -> true,
                (name, id) -> false,
                id -> Optional.of(new EquipmentEntity()),
                muscleEngagementEntities -> List.of()
        ));
    }

    @Test
    public void validateUpdateExercise_whenEquipmentNotExistsById_shouldThrowEntityNotFoundException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateUpdateExercise(
                        exerciseEntity,
                        id -> true,
                        (name, id) -> false,
                        id -> Optional.empty(),
                        muscleEngagementEntities -> List.of()
                )
        );
    }

    // ----------------------------------------
    //        TESTS FOR PARTIAL UPDATE EXERCISE
    // ----------------------------------------

    @Test
    public void validatePartialUpdateExercise_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validatePartialUpdateExercise(new ExerciseEntity(), null, null, null, null)
        );
    }

    @Test
    public void validatePartialUpdateExercise_whenExerciseNotExistsById_shouldThrowEntityNotFoundException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, null, null);
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validatePartialUpdateExercise(exerciseEntity, id -> Optional.empty(), null, null, null)
        );
    }

    @Test
    public void validatePartialUpdateExercise_whenEquipmentNotExistsById_shouldThrowEntityNotFoundException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, null, null, new EquipmentEntity(1L, null), null);
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validatePartialUpdateExercise(exerciseEntity, id -> Optional.of(exerciseEntity), null, id -> Optional.empty(), null)
        );
    }

    @Test
    public void validatePartialUpdateExercise_whenExerciseExistsByNameAndEquipmentId_shouldThrowEntityExistsException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, "name1", "description", new EquipmentEntity(1L, null), List.of());
        EquipmentEntity persistedEquipment = new EquipmentEntity(1L, "Equipment");
        ExerciseEntity persistedExercise = new ExerciseEntity(1L, "name2", "description", new EquipmentEntity(1L, null), List.of());

        assertThrows(EntityExistsException.class,
                () -> serviceValidator.validatePartialUpdateExercise(
                        exerciseEntity,
                        id -> Optional.of(persistedExercise),
                        (name, id) -> true,
                        id -> Optional.of(persistedEquipment),
                        muscleEngagements -> List.of()
                )
        );
    }

    @Test
    public void validatePartialUpdateExercise_whenAllArgumentsAreCorrect_shouldNotThrowException() {
        ExerciseEntity exerciseEntity = new ExerciseEntity(1L, "name1", "description", new EquipmentEntity(1L, null), List.of());
        EquipmentEntity persistedEquipment = new EquipmentEntity(1L, "Equipment");
        ExerciseEntity persistedExercise = new ExerciseEntity(1L, "name2", "description", new EquipmentEntity(1L, null), List.of());

        assertDoesNotThrow(() -> serviceValidator.validatePartialUpdateExercise(
                exerciseEntity,
                id -> Optional.of(persistedExercise),
                (name, id) -> false,
                id -> Optional.of(persistedEquipment),
                muscleEngagements -> List.of()
        ));
    }

    // ----------------------------------------
    //        TESTS FOR DELETE EXERCISE
    // ----------------------------------------

    @Test
    public void validateDeleteExercise_whenIdIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.validateDeleteExercise(null, null)
        );
    }

    @Test
    public void validateDeleteExercise_whenNotExistsById_shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
                () -> serviceValidator.validateDeleteExercise(1L, id -> false)
        );
    }

    @Test
    public void validateDeleteExercise_whenExistsById_shouldNotThrowException() {
        assertDoesNotThrow(() -> serviceValidator.validateDeleteExercise(1L, id -> true));
    }
}
