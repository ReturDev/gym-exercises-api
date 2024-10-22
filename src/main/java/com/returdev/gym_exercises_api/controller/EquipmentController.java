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

@RestController
@RequestMapping("v1/equipment")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EquipmentController {

    private static final String EQUIPMENT_RESOURCE_PATH = "v1/equipment";

    private final EquipmentService equipmentService;
    private final EntityDtoMapper mapper;


    @GetMapping
    public ResponseEntity<PaginationResponseDTO<EquipmentResponseDTO>> getEquipments(
    @Valid EquipmentPaginationRequestDTO paginationRequestDTO
    ) {

        Page<EquipmentEntity> page = equipmentService.getAllEquipments(
                mapper.paginationRequestDtoToPageable(paginationRequestDTO)
        );

        return ResponseEntity.ok(
                mapper.equipmentEntityToContentResponse(page)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> getEquipmentById(
            @PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(
                mapper.equipmentEntityToContentResponse(
                        equipmentService.getEquipmentById(id)
                )
        );

    }


    @PostMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> saveEquipment(
            @RequestBody @Valid EquipmentRequestDTO equipmentRequestDTO
    ) {

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

    @PutMapping()
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<ContentResponseDTO<EquipmentResponseDTO>> updateEquipment(
            @RequestBody @Valid EquipmentRequestDTO equipmentRequestDTO
    ) {
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<Void> deleteEquipment(
            @PathVariable("id") Long id
    ) {

        equipmentService.deleteEquipment(id);

        return ResponseEntity.noContent().build();

    }


}
