package com.returdev.gym_exercises_api.repositories.security;

import com.returdev.gym_exercises_api.model.auth.AuthToken;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class TokenRepositoryImpl implements TokenRepository{

    private AuthToken currentToken;

    @Override
    public void updateToken(AuthToken newToken) {
        currentToken = newToken;
    }

    @Override
    public AuthToken getToken() {
        return currentToken;
    }

}
