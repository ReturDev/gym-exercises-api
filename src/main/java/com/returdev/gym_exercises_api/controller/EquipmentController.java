package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.EquipmentPaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.EquipmentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.PaginationResponseDTO;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.service.data.equipment.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * REST controller for managing equipment.
 *
 * <p>
 * This controller provides endpoints to create, read, update, and delete equipment.
 * It requires authentication for all actions and specific roles for modifying equipment.
 * </p>
 */
@RestController
@RequestMapping("v1/equipment")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EquipmentController {

    private static final String EQUIPMENT_RESOURCE_PATH = "v1/equipment";

    private final EquipmentService equipmentService;
    private final EntityDtoMapper mapper;

    /**
     * Retrieves a paginated list of equipment.
     *
     * @param paginationRequestDTO The pagination parameters.
     * @return A response entity containing a paginated list of equipment.
     */
    @GetMapping
    public ResponseEntity<PaginationResponseDTO<EquipmentResponseDTO>> getEquipments(
            @Valid EquipmentPaginationRequestDTO paginationRequestDTO
    ) {
        // Fetch paginated equipment entities from the service
        Page<EquipmentEntity> page = equipmentService.getAllEquipments(
                mapper.paginationRequestDtoToPageable(paginationRequestDTO)
        );

        // Map the equipment entities to response DTOs and return them
        return ResponseEntity.ok(
                mapper.equipmentEntityToContentResponse(page)
        );
    }

    /**
     * Retrieves equipment by its ID.
     *
     * @param id The ID of the equipment to retrieve.
     * @return A response entity containing the equipment details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> getEquipmentById(
            @PathVariable("id") Long id
    ) {
        // Fetch the equipment entity by ID and map it to response DTO
        return ResponseEntity.ok(
                mapper.equipmentEntityToContentResponse(
                        equipmentService.getEquipmentById(id)
                )
        );
    }

    /**
     * Creates a new equipment.
     *
     * @param equipmentRequestDTO The equipment data to create.
     * @return A response entity containing the created equipment details.
     */
    @PostMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> saveEquipment(
            @RequestBody @Valid EquipmentRequestDTO equipmentRequestDTO
    ) {
        // Save the equipment entity and map it to response DTO
        EquipmentEntity equipmentSaved = equipmentService.saveEquipment(
                mapper.equipmentRequestDtoToEntity(equipmentRequestDTO)
        );

        URI equipmentSavedUri = URI.create(
                EQUIPMENT_RESOURCE_PATH + "/" + equipmentSaved.getId()
        );

        return ResponseEntity.created(equipmentSavedUri)
                .body(
                        mapper.equipmentEntityToContentResponse(equipmentSaved)
                );
    }

    /**
     * Updates an existing equipment.
     *
     * @param equipmentRequestDTO The updated equipment data.
     * @return A response entity containing the updated equipment details.
     */
    @PutMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> updateEquipment(
            @RequestBody @Valid EquipmentRequestDTO equipmentRequestDTO
    ) {
        // Update the equipment entity and map it to response DTO
        EquipmentEntity equipmentUpdated = equipmentService.updateEquipment(
                mapper.equipmentRequestDtoToEntity(equipmentRequestDTO)
        );

        URI equipmentUpdatedUri = URI.create(
                EQUIPMENT_RESOURCE_PATH + "/" + equipmentUpdated.getId()
        );

        return ResponseEntity.ok()
                .location(equipmentUpdatedUri)
                .body(
                        mapper.equipmentEntityToContentResponse(equipmentUpdated)
                );
    }

    /**
     * Deletes equipment by its ID.
     *
     * @param id The ID of the equipment to delete.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<Void> deleteEquipment(
            @PathVariable("id") Long id
    ) {
        // Delete the equipment by ID
        equipmentService.deleteEquipment(id);

        return ResponseEntity.noContent().build();
    }

}

