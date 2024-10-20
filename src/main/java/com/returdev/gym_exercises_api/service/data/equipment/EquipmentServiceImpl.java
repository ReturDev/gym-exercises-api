package com.returdev.gym_exercises_api.service.data.equipment;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.repositories.data.EquipmentRepository;
import com.returdev.gym_exercises_api.service.data.validators.ServiceValidator;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final ServiceValidator serviceValidator;
    private final EquipmentRepository equipmentRepository;


    public EquipmentServiceImpl(ServiceValidator serviceValidator, EquipmentRepository equipmentRepository) {
        this.serviceValidator = serviceValidator;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Optional<EquipmentEntity> getEquipmentById(Long id) {

        serviceValidator.validateGetEquipmentById(id);

        return equipmentRepository.findById(id);

    }

    @Override
    public Page<EquipmentEntity> getAllEquipments(Pageable pageable) {
        return  equipmentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public EquipmentEntity saveEquipment(EquipmentEntity equipment) {

        serviceValidator.validateSaveEquipment(
                equipment,
                equipmentRepository::existsByName
        );

        return equipmentRepository.save(equipment);

    }

    @Override
    @Transactional
    public EquipmentEntity updateEquipment(EquipmentEntity equipment) {

        serviceValidator.validateUpdateEquipment(
                equipment,
                equipmentRepository::existsById,
                equipmentRepository::existsByName
        );

        return equipmentRepository.save(equipment);

    }

    @Override
    @Transactional
    public void deleteEquipment(Long id) {

        serviceValidator.validateDeleteEquipment(
                id,
                equipmentRepository::existsById
        );

        equipmentRepository.deleteById(id);

    }

}
