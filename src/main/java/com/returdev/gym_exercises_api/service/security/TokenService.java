package com.returdev.gym_exercises_api.service.security;

import com.returdev.gym_exercises_api.model.auth.AuthToken;

public interface TokenService {

    AuthToken getToken();

}
