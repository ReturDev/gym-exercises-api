package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.dto.response.TokenResponseDTO;
import com.returdev.gym_exercises_api.manager.message.MessageManager;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import com.returdev.gym_exercises_api.repositories.security.TokenRepository;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PermitAll
@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenRepository repository;
    private final EntityDtoMapper mapper;
    private final MessageManager messageManager;

    @GetMapping()
    public ResponseEntity<TokenResponseDTO> getUserToken(){
        return ResponseEntity.ok(
                mapper.authTokenToResponse(
                        repository.getToken()
                )
        );
    }

}
