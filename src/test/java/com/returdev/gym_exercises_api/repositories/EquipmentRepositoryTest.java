package com.returdev.gym_exercises_api.repositories;

import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class EquipmentRepositoryTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    private final String EQUIPMENT_NAME = "Dumbbells";

    @BeforeEach
    void setUp() {
        EquipmentEntity equipmentEntity = new EquipmentEntity(null, EQUIPMENT_NAME);
        equipmentRepository.save(equipmentEntity);
    }

    @Test
    void findIdByName() {

        Optional<Long> result = equipmentRepository.findIdByName(EQUIPMENT_NAME);

        assertTrue(result.isPresent());
        assertEquals(1, result.get());

    }

    @Test
    void existsByName() {

        boolean result = equipmentRepository.existsByName(EQUIPMENT_NAME);

        assertTrue(result);

    }

}