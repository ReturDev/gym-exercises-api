package com.returdev.gym_exercises_api.service.data.equipment;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

/**
 * Service interface for managing equipment entities.
 *
 * <p>
 * This interface provides methods for retrieving, saving, updating, and deleting
 * equipment entities in the system. It also supports pagination for listing all
 * equipment entries.
 * </p>
 */
@Validated
public interface EquipmentService {

    /**
     * Retrieves an equipment entity by its ID.
     *
     * <p>
     * Validates that the ID is not null and exists before retrieving the entity.
     * </p>
     *
     * @param id the ID of the equipment to retrieve
     * @return the {@link EquipmentEntity} corresponding to the given ID
     */
    EquipmentEntity getEquipmentById(Long id);

    /**
     * Retrieves all equipment entities in a paginated format.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link EquipmentEntity} objects
     */
    Page<EquipmentEntity> getAllEquipments(Pageable pageable);

    /**
     * Saves a new equipment entity after validation.
     *
     * <p>
     * The input equipment entity is validated for required fields and constraints
     * before it is persisted to the database.
     * </p>
     *
     * @param equipment the equipment entity to save
     * @return the saved {@link EquipmentEntity}
     */
    EquipmentEntity saveEquipment(@Valid EquipmentEntity equipment);

    /**
     * Updates an existing equipment entity after validation.
     *
     * <p>
     * The input equipment entity is validated and checked for existing conflicts
     * before it is updated in the database.
     * </p>
     *
     * @param equipment the equipment entity containing updated information
     * @return the updated {@link EquipmentEntity}
     */
    EquipmentEntity updateEquipment(@Valid EquipmentEntity equipment);

    /**
     * Deletes an equipment entity by its ID.
     *
     * <p>
     * Validates that the ID exists before deleting the corresponding entity from
     * the database.
     * </p>
     *
     * @param id the ID of the equipment to delete
     */
    void deleteEquipment(Long id);

}

