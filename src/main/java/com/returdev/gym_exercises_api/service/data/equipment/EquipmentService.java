package com.returdev.gym_exercises_api.service.data.equipment;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EquipmentService {

    Optional<EquipmentEntity> getEquipmentById(Long id);

    Page<EquipmentEntity> getAllEquipments(Pageable pageable);

    EquipmentEntity saveEquipment(EquipmentEntity equipment);

    EquipmentEntity updateEquipment(EquipmentEntity equipment);

    void deleteEquipment(Long id);

}
