package com.returdev.gym_exercises_api.service.security;

import com.returdev.gym_exercises_api.manager.security.JwtManager;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import com.returdev.gym_exercises_api.repositories.security.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link TokenService} interface for managing authentication tokens.
 *
 * <p>
 * This service is responsible for generating and retrieving authentication tokens.
 * It uses a {@link JwtManager} to generate tokens and a {@link TokenRepository}
 * to store and retrieve them. The service also schedules periodic token generation
 * to ensure that tokens remain valid and up-to-date.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtManager jwtManager;
    private final TokenRepository tokenRepository;

    /**
     * Periodically generates a new user token and updates the token repository.
     *
     * <p>
     * This method is scheduled to run at a fixed rate defined by the
     * {@code spring.jwt.token-expiration-millis} property. It calls
     * {@link JwtManager#generateUserToken()} to create a new token and
     * updates the token in the repository using {@link TokenRepository#updateToken(AuthToken)}.
     * </p>
     */
    @Scheduled(fixedRateString = "${spring.jwt.token-expiration-millis}")
    void scheduleUserTokenGeneration() {
        tokenRepository.updateToken(
                jwtManager.generateUserToken()
        );
    }

    /**
     * Retrieves the current authentication token from the token repository.
     *
     * @return an {@link AuthToken} representing the current authentication token.
     */
    @Override
    public AuthToken getToken() {
        return tokenRepository.getToken();
    }
}
