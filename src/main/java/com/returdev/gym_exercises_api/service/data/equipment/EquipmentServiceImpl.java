package com.returdev.gym_exercises_api.service.data.equipment;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.repositories.data.EquipmentRepository;
import com.returdev.gym_exercises_api.service.data.validators.ServiceValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link EquipmentService} interface.
 *
 * <p>
 * This class provides the concrete implementation for managing equipment entities,
 * including validation logic, persistence operations, and transaction management.
 * It utilizes a {@link ServiceValidator} to validate operations and an
 * {@link EquipmentRepository} for persistence.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final ServiceValidator serviceValidator;
    private final EquipmentRepository equipmentRepository;

    /**
     * {@inheritDoc}
     *
     * <p>
     * Validates the ID before retrieving the equipment entity. If the ID is invalid
     * or the entity doesn't exist, appropriate exceptions are thrown.
     * </p>
     *
     * @param id the ID of the equipment to retrieve
     * @return the {@link EquipmentEntity} associated with the given ID
     */
    @Override
    public EquipmentEntity getEquipmentById(Long id) {
        return serviceValidator.validateGetEquipmentById(
                id,
                equipmentRepository::findById
        );
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Retrieves all equipment entities in a paginated format. Pagination is
     * managed via the {@link Pageable} parameter.
     * </p>
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link EquipmentEntity} objects
     */
    @Override
    public Page<EquipmentEntity> getAllEquipments(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Validates the equipment entity before saving it to the database. This
     * includes checks for unique names and other constraints.
     * The operation is transactional to ensure data consistency.
     * </p>
     *
     * @param equipment the equipment entity to save
     * @return the saved {@link EquipmentEntity}
     */
    @Override
    @Transactional
    public EquipmentEntity saveEquipment(EquipmentEntity equipment) {
        serviceValidator.validateSaveEquipment(
                equipment,
                equipmentRepository::existsByName
        );
        return equipmentRepository.save(equipment);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Validates the equipment entity before updating it. This includes ensuring
     * the entity exists and that no naming conflicts occur. The operation is
     * transactional to prevent partial updates.
     * </p>
     *
     * @param equipment the equipment entity containing updated information
     * @return the updated {@link EquipmentEntity}
     */
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

    /**
     * {@inheritDoc}
     *
     * <p>
     * Validates the ID of the equipment entity before deleting it. This ensures
     * that only existing entities can be deleted. The operation is transactional
     * to maintain consistency.
     * </p>
     *
     * @param id the ID of the equipment to delete
     */
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

