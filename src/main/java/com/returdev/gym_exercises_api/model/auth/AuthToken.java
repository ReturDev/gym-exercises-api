package com.returdev.gym_exercises_api.model.auth;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

/**
 * Represents an authentication token used for API access.
 *
 * <p>
 * The {@link AuthToken} record holds the token string and its expiration time,
 * which are essential for authenticating requests to the API.
 * The token must be a non-blank string, and the expiration time indicates
 * when the token will no longer be valid.
 * </p>
 *
 * @param token the authentication token string
 * @param expirationTime the timestamp indicating when the token expires
 */
public record AuthToken(
        @NotBlank String token,
        @Nonnull Instant expirationTime
) {}
