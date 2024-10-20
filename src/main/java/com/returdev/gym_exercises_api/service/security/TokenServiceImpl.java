package com.returdev.gym_exercises_api.service.security;

import com.returdev.gym_exercises_api.model.auth.AuthToken;
import com.returdev.gym_exercises_api.manager.security.JwtManager;
import com.returdev.gym_exercises_api.repositories.security.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtManager jwtManager;
    private final TokenRepository tokenRepository;

    @Scheduled(fixedRateString = "${spring.jwt.token-expiration-millis}")
    void scheduleUserTokenGeneration(){

        tokenRepository.updateToken(
                jwtManager.generateUserToken()
        );

    }

    @Override
    public AuthToken getToken() {
        return tokenRepository.getToken();
    }
}
