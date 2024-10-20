package com.returdev.gym_exercises_api.repositories.security;


import com.returdev.gym_exercises_api.model.auth.AuthToken;

public interface TokenRepository {

    void updateToken(AuthToken newToken);

    AuthToken getToken();

}
