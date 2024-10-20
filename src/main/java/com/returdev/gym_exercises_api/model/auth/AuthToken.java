package com.returdev.gym_exercises_api.model.auth;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record AuthToken(
        @NotBlank String token,
        @Nonnull Instant expirationTime
){}
