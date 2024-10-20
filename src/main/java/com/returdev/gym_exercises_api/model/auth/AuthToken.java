package com.returdev.gym_exercises_api.model.auth;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public record AuthToken(
        @NotBlank String token,
        @Nonnull java.time.Instant tokenExpirationTime
){}
