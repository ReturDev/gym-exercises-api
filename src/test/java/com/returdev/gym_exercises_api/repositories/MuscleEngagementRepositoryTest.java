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

    private final MuscleEngagementEntity muscleEngagementEntity = new MuscleEngagementEntity(
            null,
            Muscle.BICEPS,
            MuscleActivationLevel.HIGH,
            null
    );

    @BeforeEach
    void setUp() {
        repository.save(muscleEngagementEntity);
    }

    @Test
    void findByMuscle() {

        List<MuscleEngagementEntity> muscles = repository.findByMuscle(Muscle.BICEPS);

        assertFalse(muscles.isEmpty());
        assertEquals(muscleEngagementEntity, muscles.get(0));

    }

    @Test
    void findByMuscleActivationLevel() {

        List<MuscleEngagementEntity> muscles = repository.findByMuscleActivationLevel(MuscleActivationLevel.HIGH);

        assertFalse(muscles.isEmpty());
        assertEquals(muscleEngagementEntity, muscles.get(0));

    }

    @Test
    void findIdByMuscleAndActivationLevel() {

        Optional<Long> id = repository.findIdByMuscleAndActivationLevel(Muscle.BICEPS, MuscleActivationLevel.HIGH);

        assertTrue(id.isPresent());

    }

}