package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.config.ApplicationConfig;
import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.EquipmentPaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.EquipmentResponseDTO;
import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApplicationConfig.API_VERSION + "/equipment")
@AllArgsConstructor
public class EquipmentController {

    private static final String EQUIPMENT_RESOURCE_PATH = ApplicationConfig.API_VERSION + "/equipment";

    private final EquipmentService equipmentService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<ContentResponseDTO<List<EquipmentResponseDTO>>> getEquipments(
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
        return equipmentService.getEquipmentById(id)
                .map(equipment ->
                        ResponseEntity.ok(
                                mapper.equipmentEntityToContentResponse(equipment)
                        )
                ).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping()
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
    public ResponseEntity<Void> deleteEquipment(
            @PathVariable("id") Long id
    ) {

        equipmentService.deleteEquipment(id);

        return ResponseEntity.noContent().build();

    }


}
