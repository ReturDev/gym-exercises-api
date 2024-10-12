package com.returdev.gym_exercises_api.mappers;

import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.MuscleEngagementRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.PaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.EquipmentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.ExerciseResponseDTO;
import com.returdev.gym_exercises_api.dto.response.MuscleEngagementResponseDTO;
import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityDtoMapperImpl implements EntityDtoMapper {

    @Override
    public ContentResponseDTO<List<EquipmentResponseDTO>> equipmentEntityToContentResponse(Page<EquipmentEntity> equipmentEntityPage) {

        ContentResponseDTO<List<EquipmentResponseDTO>> contentResponseDTO = new ContentResponseDTO<>();

        contentResponseDTO.setContent(
                equipmentEntityPage.getContent().stream().map(this::equipmentEntityToResponseDto).toList()
        );

        contentResponseDTO.setPageInfo(
                pageToPageInfo(equipmentEntityPage)
        );

        return contentResponseDTO;
    }

    @Override
    public ContentResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(EquipmentEntity equipmentEntity) {
        return new ContentResponseDTO<>(
                equipmentEntityToResponseDto(equipmentEntity),
                null
        );
    }

    @Override
    public EquipmentEntity equipmentRequestDtoToEntity(EquipmentRequestDTO equipmentRequestDTO) {
        return new EquipmentEntity(
                equipmentRequestDTO.id(),
                equipmentRequestDTO.name()
        );
    }

    @Override
    public ContentResponseDTO<List<ExerciseResponseDTO>> exerciseEntityToContentResponse(Page<ExerciseEntity> exerciseEntityPage) {
        ContentResponseDTO<List<ExerciseResponseDTO>> contentResponseDTO = new ContentResponseDTO<>();

        contentResponseDTO.setContent(
                exerciseEntityPage.getContent().stream().map(this::exerciseEntityToResponseDto).toList()
        );

        contentResponseDTO.setPageInfo(
                pageToPageInfo(exerciseEntityPage)
        );

        return contentResponseDTO;
    }

    @Override
    public ContentResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(ExerciseEntity exerciseEntity) {
        return new ContentResponseDTO<>(
                exerciseEntityToResponseDto(exerciseEntity),
                null
        );
    }

    @Override
    public ExerciseEntity exerciseRequestDtoToEntity(ExerciseRequestDTO exerciseRequestDTO) {
        return new ExerciseEntity(
                exerciseRequestDTO.id(),
                exerciseRequestDTO.name(),
                exerciseRequestDTO.description(),
                new EquipmentEntity(exerciseRequestDTO.equipmentId(), null),
                exerciseRequestDTO.muscleEngagements().stream()
                        .map(this::muscleEngagementRequestDtoToEntity)
                        .toList()
        );
    }

    @Override
    public Pageable paginationRequestDtoToPageable(PaginationRequestDTO paginationRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequestDTO.getSortDirection()), paginationRequestDTO.getOrderBy());

        return PageRequest.of(
                paginationRequestDTO.getPage() - 1,
                paginationRequestDTO.getPageSize(),
                sort
        );
    }

    private <T> ContentResponseDTO.PageInfo pageToPageInfo(Page<T> page) {
        return new ContentResponseDTO.PageInfo(
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1
        );
    }

    private EquipmentResponseDTO equipmentEntityToResponseDto(EquipmentEntity equipmentEntity) {
        return new EquipmentResponseDTO(
                equipmentEntity.getId(),
                equipmentEntity.getName()
        );
    }

    private ExerciseResponseDTO exerciseEntityToResponseDto(ExerciseEntity exerciseEntity) {
        return new ExerciseResponseDTO(
                exerciseEntity.getId(),
                exerciseEntity.getName(),
                exerciseEntity.getDescription(),
                this.equipmentEntityToResponseDto(exerciseEntity.getEquipment()),
                exerciseEntity.getMusclesEngagement().stream()
                        .map(this::muscleEngagementEntityToResponseDto)
                        .toList()
        );
    }

    private MuscleEngagementResponseDTO muscleEngagementEntityToResponseDto(MuscleEngagementEntity muscleEngagementEntity) {
        return new MuscleEngagementResponseDTO(
                muscleEngagementEntity.getId(),
                muscleEngagementEntity.getMuscle().name(),
                muscleEngagementEntity.getMuscleActivationLevel().name()
        );
    }

    private MuscleEngagementEntity muscleEngagementRequestDtoToEntity(MuscleEngagementRequestDTO muscleEngagementRequestDTO) {
        return new MuscleEngagementEntity(
                null,
                muscleEngagementRequestDTO.muscle(),
                muscleEngagementRequestDTO.muscleActivationLevel(),
                null
        );
    }

}
