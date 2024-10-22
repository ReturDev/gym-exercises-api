package com.returdev.gym_exercises_api.mappers;

import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.dto.request.pagination.PaginationRequestDTO;
import com.returdev.gym_exercises_api.dto.response.*;
import com.returdev.gym_exercises_api.dto.response.wrapper.ContentResponseDTO;
import com.returdev.gym_exercises_api.dto.response.wrapper.PaginationResponseDTO;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for mapping entities to Data Transfer Objects (DTOs)
 * and vice versa within the application.
 *
 * <p>
 * This interface provides methods to convert between entity objects
 * (such as {@link EquipmentEntity} and {@link ExerciseEntity}) and
 * their corresponding DTO representations (like {@link EquipmentResponseDTO}
 * and {@link ExerciseResponseDTO}). It also handles pagination
 * requests and authentication token responses.
 * </p>
 */
public interface EntityDtoMapper {

    /**
     * Converts a page of equipment entities to a paginated response DTO.
     *
     * @param equipmentEntityPage the page of equipment entities
     * @return a {@link PaginationResponseDTO} containing the equipment response DTOs
     */
    PaginationResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(Page<EquipmentEntity> equipmentEntityPage);

    /**
     * Converts a single equipment entity to a content response DTO.
     *
     * @param equipmentEntity the equipment entity to convert
     * @return a {@link ContentResponseDTO} containing the equipment response DTO
     */
    ContentResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(EquipmentEntity equipmentEntity);

    /**
     * Converts an equipment request DTO to an equipment entity.
     *
     * @param equipmentRequestDTO the equipment request DTO to convert
     * @return the corresponding {@link EquipmentEntity}
     */
    EquipmentEntity equipmentRequestDtoToEntity(EquipmentRequestDTO equipmentRequestDTO);

    /**
     * Converts a page of exercise entities to a paginated response DTO.
     *
     * @param exerciseEntityPage the page of exercise entities
     * @return a {@link PaginationResponseDTO} containing the exercise response DTOs
     */
    PaginationResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(Page<ExerciseEntity> exerciseEntityPage);

    /**
     * Converts a single exercise entity to a content response DTO.
     *
     * @param exerciseEntity the exercise entity to convert
     * @return a {@link ContentResponseDTO} containing the exercise response DTO
     */
    ContentResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(ExerciseEntity exerciseEntity);

    /**
     * Converts an exercise request DTO to an exercise entity.
     *
     * @param exerciseRequestDTO the exercise request DTO to convert
     * @return the corresponding {@link ExerciseEntity}
     */
    ExerciseEntity exerciseRequestDtoToEntity(ExerciseRequestDTO exerciseRequestDTO);

    /**
     * Converts a pagination request DTO to a pageable object.
     *
     * @param paginationRequestDTO the pagination request DTO to convert
     * @return the corresponding {@link Pageable} object
     */
    Pageable paginationRequestDtoToPageable(PaginationRequestDTO paginationRequestDTO);

    /**
     * Converts an authentication token to a token response DTO.
     *
     * @param authToken the authentication token to convert
     * @return the corresponding {@link TokenResponseDTO}
     */
    TokenResponseDTO authTokenToResponse(AuthToken authToken);
}
