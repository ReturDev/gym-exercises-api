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

public interface EntityDtoMapper {

    PaginationResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(Page<EquipmentEntity> equipmentEntityPage);
    ContentResponseDTO<EquipmentResponseDTO> equipmentEntityToContentResponse(EquipmentEntity equipmentEntity);
    EquipmentEntity equipmentRequestDtoToEntity(EquipmentRequestDTO equipmentRequestDTO);

    PaginationResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(Page<ExerciseEntity> exerciseEntityPage);
    ContentResponseDTO<ExerciseResponseDTO> exerciseEntityToContentResponse(ExerciseEntity exerciseEntity);
    ExerciseEntity exerciseRequestDtoToEntity(ExerciseRequestDTO exerciseRequestDTO);

    Pageable paginationRequestDtoToPageable(PaginationRequestDTO paginationRequestDTO);

    TokenResponseDTO authTokenToResponse(AuthToken authToken);

}