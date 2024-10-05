package com.returdev.gym_exercises_api.repositories;

import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.enums.Muscle;
import com.returdev.gym_exercises_api.enums.MuscleActivationLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private MuscleEngagementRepository muscleEngagementRepository;

    private final List<EquipmentEntity> equipmentEntityList = List.of(
            new EquipmentEntity(
                    null,
                    "Dumbbells"
            ),
            new EquipmentEntity(
                    null,
                    "Bar"
            ),
            new EquipmentEntity(
                    null,
                    "Machine"
            )
    );

    private final List<MuscleEngagementEntity> muscleEngagementEntityList = List.of(
            new MuscleEngagementEntity(null, Muscle.BICEPS, MuscleActivationLevel.HIGH, null),
            new MuscleEngagementEntity(null, Muscle.DORSALS, MuscleActivationLevel.LOW, null),
            new MuscleEngagementEntity(null, Muscle.HAMSTRINGS, MuscleActivationLevel.LOW, null),
            new MuscleEngagementEntity(null, Muscle.FOREARMS, MuscleActivationLevel.MEDIUM, null)
    );

    private final List<ExerciseEntity> exerciseEntityList = List.of(
            new ExerciseEntity(
                    null,
                    "Exercise1",
                    "Description1",
                    equipmentEntityList.get(0),
                    muscleEngagementEntityList.subList(0, 2)
            ),
            new ExerciseEntity(
                    null,
                    "Exercise2",
                    "Description2",
                    equipmentEntityList.get(1),
                    muscleEngagementEntityList.subList(1, 3)
            ),
            new ExerciseEntity(
                    null,
                    "Exercise3",
                    "Description3",
                    equipmentEntityList.get(2),
                    muscleEngagementEntityList.subList(3, 4)
            )
    );


    @BeforeEach
    void setUp() {

        equipmentRepository.saveAll(equipmentEntityList);
        muscleEngagementRepository.saveAll(muscleEngagementEntityList);
        exerciseRepository.saveAll(exerciseEntityList);

    }

    @Test
    void findByMuscle() {

        Page<ExerciseEntity> exercisePage = exerciseRepository.findByMuscle(Muscle.BICEPS, Pageable.unpaged());

        List<ExerciseEntity> pageContent = exercisePage.getContent();

        assertFalse(pageContent.isEmpty());

    }

    @Test
    void findByMuscles() {

        Page<ExerciseEntity> exercisePage = exerciseRepository.findByMuscles(
                List.of(Muscle.BICEPS, Muscle.HAMSTRINGS), Pageable.unpaged());

        List<ExerciseEntity> pageContent = exercisePage.getContent();

        assertEquals(2, pageContent.size());

    }

    @Test
    void findByMuscleEngagementId() {

        Optional<Long> muscleEngagementId = muscleEngagementRepository.findIdByMuscleAndActivationLevel(Muscle.BICEPS, MuscleActivationLevel.HIGH);
        muscleEngagementId.ifPresentOrElse((id) -> {

                    Page<ExerciseEntity> exercisePage = exerciseRepository.findByMuscleEngagementId(id, Pageable.unpaged());
                    List<ExerciseEntity> pageContent = exercisePage.getContent();

                    assertEquals(exerciseEntityList.get(0).getName(), pageContent.get(0).getName());
                },
                Assertions::fail
        );

    }

    @Test
    void findByMuscleEngagementIds() {

        List<Long> muscleEngagementIDs = muscleEngagementRepository.findAll().subList(0, 2).stream().map(MuscleEngagementEntity::getId).toList();

        Page<ExerciseEntity> exerciseEntityPage = exerciseRepository.findByMuscleEngagementIds(muscleEngagementIDs, Pageable.unpaged());
        List<ExerciseEntity> pageContent = exerciseEntityPage.getContent();
        assertFalse(pageContent.isEmpty());
        assertEquals(2, pageContent.size());

    }

    @Test
    void findByEquipmentId() {

        Optional<Long> equipmentId = equipmentRepository.findIdByName(equipmentEntityList.get(0).getName());

        equipmentId.ifPresentOrElse((id) -> {
            Page<ExerciseEntity> exerciseEntity = exerciseRepository.findByEquipmentId(id, Pageable.unpaged());
            List<ExerciseEntity> pageContent = exerciseEntity.getContent();

            assertFalse(pageContent.isEmpty());


        }, Assertions::fail);

    }

    @Test
    void existsByNameAndEquipmentId() {

        Optional<Long> equipmentId = equipmentRepository.findIdByName(equipmentEntityList.get(0).getName());
        String exerciseName = exerciseEntityList.get(0).getName();

        equipmentId.ifPresentOrElse((id) -> {

            boolean exists = exerciseRepository.existsByNameAndEquipmentId(exerciseName, id);
            assertTrue(exists);


        }, Assertions::fail);

    }
}