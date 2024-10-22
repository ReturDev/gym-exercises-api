package com.returdev.gym_exercises_api.service.data.equipment;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface EquipmentService {

    Optional<EquipmentEntity> getEquipmentById(Long id);

    Page<EquipmentEntity> getAllEquipments(Pageable pageable);

    EquipmentEntity saveEquipment(@Valid EquipmentEntity equipment);

    EquipmentEntity updateEquipment(@Valid EquipmentEntity equipment);

    void deleteEquipment(Long id);

}
