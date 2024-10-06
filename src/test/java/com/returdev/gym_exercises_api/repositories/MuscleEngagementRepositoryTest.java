package com.returdev.gym_exercises_api.repositories;

import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.enums.Muscle;
import com.returdev.gym_exercises_api.enums.MuscleActivationLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class MuscleEngagementRepositoryTest {

    @Autowired
    private MuscleEngagementRepository repository;

    private final List<MuscleEngagementEntity> muscleEngagementEntities = List.of(
            new MuscleEngagementEntity(null, Muscle.BICEPS, MuscleActivationLevel.HIGH, null),
            new MuscleEngagementEntity(null, Muscle.TRAPEZES, MuscleActivationLevel.HIGH, null),
            new MuscleEngagementEntity(null, Muscle.BUTTOCKS, MuscleActivationLevel.MEDIUM, null),
            new MuscleEngagementEntity(null, Muscle.ABS, MuscleActivationLevel.LOW, null),
            new MuscleEngagementEntity(null, Muscle.WIDE_BACK, MuscleActivationLevel.LOW, null),
            new MuscleEngagementEntity(null, Muscle.LOWER_CHEST, MuscleActivationLevel.MEDIUM, null)
    );

    @BeforeEach
    void setUp() {
        repository.saveAll(muscleEngagementEntities);
    }

    @Test
    void findByMuscle() {

        List<MuscleEngagementEntity> muscles = repository.findByMuscle(Muscle.BICEPS);

        assertFalse(muscles.isEmpty());
        assertEquals(muscleEngagementEntities.get(0), muscles.get(0));

    }

    @Test
    void findByMuscleActivationLevel() {

        List<MuscleEngagementEntity> muscles = repository.findByMuscleActivationLevel(MuscleActivationLevel.HIGH);

        assertFalse(muscles.isEmpty());
        assertEquals(muscleEngagementEntities.get(0), muscles.get(0));

    }

    @Test
    void findIdByMuscleAndActivationLevel() {

        Optional<Long> id = repository.findIdByMuscleAndActivationLevel(Muscle.BICEPS, MuscleActivationLevel.HIGH);

        assertTrue(id.isPresent());

    }

    @Test
    void findByMusclesAndActivationLevels() {

        List<MuscleEngagementEntity> muscleEngagementsToFind = muscleEngagementEntities.subList(0, 3);
        List<Muscle> musclesToFind = muscleEngagementsToFind.stream()
                .map(MuscleEngagementEntity::getMuscle)
                .toList();
        List<MuscleActivationLevel> activationLevelsToFind = muscleEngagementsToFind.stream()
                .map(MuscleEngagementEntity::getMuscleActivationLevel)
                .toList();


        List<MuscleEngagementEntity> result = repository.findByMusclesAndActivationLevels(
                musclesToFind,
                activationLevelsToFind
        );

        assertFalse(result.isEmpty());
        assertEquals(muscleEngagementsToFind.size(), result.size());

    }
}