package com.returdev.gym_exercises_api.mappers;

import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.MuscleEngagementRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.PaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.*;
import com.returdev.gym_exercises_api.dto.response.wrapper.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.PaginationResponseDTO;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link EntityDtoMapper} interface for mapping between
 * entities and their corresponding DTO representations.
 *
 * <p>
 * This class handles the conversion of various entities like
 * {@link EquipmentEntity} and {@link ExerciseEntity} to their
 * respective DTOs and vice versa. It also manages pagination
 * requests and authentication tokens.
 * </p>
 */
@Component
public class EntityDtoMapperImpl implements EntityDtoMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(Page<EquipmentEntity> equipmentEntityPage) {
        return new PaginationResponseDTO<>(
                equipmentEntityPage.getContent().stream()
                        .map(this::equipmentEntityToResponseDto)
                        .toList(),
                pageToPageInfo(equipmentEntityPage)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContentResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(EquipmentEntity equipmentEntity) {
        return new ContentResponseDTO<>(
                equipmentEntityToResponseDto(equipmentEntity)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquipmentEntity equipmentRequestDtoToEntity(EquipmentRequestDTO equipmentRequestDTO) {
        return new EquipmentEntity(
                equipmentRequestDTO.id(),
                equipmentRequestDTO.name()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(Page<ExerciseEntity> exerciseEntityPage) {
        return new PaginationResponseDTO<>(
                exerciseEntityPage.getContent().stream()
                        .map(this::exerciseEntityToResponseDto)
                        .toList(),
                pageToPageInfo(exerciseEntityPage)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContentResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(ExerciseEntity exerciseEntity) {
        return new ContentResponseDTO<>(
                exerciseEntityToResponseDto(exerciseEntity)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExerciseEntity exerciseRequestDtoToEntity(ExerciseRequestDTO exerciseRequestDTO) {
        EquipmentEntity equipmentEntity = exerciseRequestDTO.equipmentId() == null ? null :
                new EquipmentEntity(exerciseRequestDTO.equipmentId(), null);

        List<MuscleEngagementEntity> muscleEngagementEntities = exerciseRequestDTO.muscleEngagements() == null ? null :
                exerciseRequestDTO.muscleEngagements().stream()
                        .map(this::muscleEngagementRequestDtoToEntity)
                        .toList();

        return new ExerciseEntity(
                exerciseRequestDTO.id(),
                exerciseRequestDTO.name(),
                exerciseRequestDTO.description(),
                equipmentEntity,
                muscleEngagementEntities
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pageable paginationRequestDtoToPageable(PaginationRequestDTO paginationRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequestDTO.getSortDirection()), paginationRequestDTO.getOrderBy());

        return PageRequest.of(
                paginationRequestDTO.getPage() - 1,
                paginationRequestDTO.getPageSize(),
                sort
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenResponseDTO authTokenToResponse(AuthToken authToken) {
        Duration tokenExpirationDuration = Duration.between(
                Instant.now(),
                authToken.expirationTime()
        );

        return new TokenResponseDTO(
                authToken.token(),
                tokenExpirationDuration
        );
    }

    /**
     * Converts a Page object into a PageInfo DTO.
     *
     * @param page the page to convert
     * @param <T> the type of content in the page
     * @return a {@link PaginationResponseDTO.PageInfo} containing the page's information
     */
    private <T> PaginationResponseDTO.PageInfo pageToPageInfo(Page<T> page) {
        return new PaginationResponseDTO.PageInfo(
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1
        );
    }

    /**
     * Converts an EquipmentEntity to an EquipmentResponseDTO.
     *
     * @param equipmentEntity the equipment entity to convert
     * @return a {@link EquipmentResponseDTO} containing the equipment's information
     */
    private EquipmentResponseDTO equipmentEntityToResponseDto(EquipmentEntity equipmentEntity) {
        return new EquipmentResponseDTO(
                equipmentEntity.getId(),
                equipmentEntity.getName()
        );
    }

    /**
     * Converts an ExerciseEntity to an ExerciseResponseDTO.
     *
     * @param exerciseEntity the exercise entity to convert
     * @return a {@link ExerciseResponseDTO} containing the exercise's information
     */
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

    /**
     * Converts a MuscleEngagementEntity to a MuscleEngagementResponseDTO.
     *
     * @param muscleEngagementEntity the muscle engagement entity to convert
     * @return a {@link MuscleEngagementResponseDTO} containing the muscle engagement's information
     */
    private MuscleEngagementResponseDTO muscleEngagementEntityToResponseDto(MuscleEngagementEntity muscleEngagementEntity) {
        return new MuscleEngagementResponseDTO(
                muscleEngagementEntity.getId(),
                muscleEngagementEntity.getMuscle().name(),
                muscleEngagementEntity.getMuscleActivationLevel().name()
        );
    }

    /**
     * Converts a MuscleEngagementRequestDTO to a MuscleEngagementEntity.
     *
     * @param muscleEngagementRequestDTO the muscle engagement request DTO to convert
     * @return a {@link MuscleEngagementEntity} containing the muscle engagement's information
     */
    private MuscleEngagementEntity muscleEngagementRequestDtoToEntity(MuscleEngagementRequestDTO muscleEngagementRequestDTO) {
        return new MuscleEngagementEntity(
                null,
                muscleEngagementRequestDTO.muscle(),
                muscleEngagementRequestDTO.muscleActivationLevel()
        );
    }
}

