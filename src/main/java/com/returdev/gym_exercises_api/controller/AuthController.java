package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.dto.response.TokenResponseDTO;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.repositories.security.TokenRepository;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication-related operations.
 *
 * <p>
 * This controller provides endpoints for user authentication, including obtaining a token.
 * It is accessible to all authenticated users.
 * </p>
 */
@PermitAll
@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenRepository repository;
    private final EntityDtoMapper mapper;

    /**
     * Retrieves the authentication token for the user.
     *
     * @return A response entity containing the token details.
     */
    @GetMapping()
    public ResponseEntity<TokenResponseDTO> getUserToken() {
        // Fetch the token from the repository and map it to the response DTO
        return ResponseEntity.ok(
                mapper.authTokenToResponse(
                        repository.getToken()
                )
        );
    }
}

