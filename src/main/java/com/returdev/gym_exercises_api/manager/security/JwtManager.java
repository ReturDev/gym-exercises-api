package com.returdev.gym_exercises_api.manager.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.returdev.gym_exercises_api.model.auth.AuthToken;

public interface JwtManager {

    AuthToken generateUserToken();

    DecodedJWT validateToken(String token);

    String getTokenRole(DecodedJWT decodedJWT);

}
