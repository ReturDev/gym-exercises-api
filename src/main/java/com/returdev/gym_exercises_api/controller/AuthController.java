package com.returdev.gym_exercises_api.controller;

import com.returdev.gym_exercises_api.annotation.swagger.response.InternalServerErrorResponseCode;
import com.returdev.gym_exercises_api.annotation.swagger.response.OkResponseCode;
import com.returdev.gym_exercises_api.dto.response.TokenResponseDTO;
import com.returdev.gym_exercises_api.mappers.EntityDtoMapper;
import com.returdev.gym_exercises_api.repositories.security.TokenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
@SecurityRequirements
public class AuthController {

    private final TokenRepository repository;
    private final EntityDtoMapper mapper;

    /**
     * Retrieves a public authentication token for testing purposes.
     *
     * <p>
     * This method provides a single public token that is valid for all users.
     * The token has an expiration time and can be used to test the API without needing individual user credentials.
     * </p>
     * @return A response entity containing the public token details.
     */
    @Operation(
            summary = "Retrieve Public Authentication Token",
            description = "Fetches a public authentication token for testing purposes, valid for all users."
    )
    @OkResponseCode
    @InternalServerErrorResponseCode
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

