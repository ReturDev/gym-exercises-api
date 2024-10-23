package com.returdev.gym_exercises_api.manager.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import jakarta.annotation.Nonnull;

/**
 * Interface for managing JSON Web Tokens (JWT) in the application.
 *
 * <p>
 * This interface defines methods for generating JWTs, validating them, and
 * extracting claims such as user roles from the tokens.
 * </p>
 */
public interface JwtManager {

    /**
     * Generates a new JWT for user authentication.
     *
     * @return an {@link AuthToken} containing the generated token and its expiration time
     */
    AuthToken generateUserToken();

    /**
     * Validates a given JWT and returns its decoded representation if valid.
     *
     * @param token the JWT to validate
     * @return the decoded JWT if the token is valid
     * @throws JWTVerificationException if the token is invalid or has expired
     */
    DecodedJWT validateToken(@Nonnull String token) throws JWTVerificationException;

    /**
     * Extracts the role claim from a decoded JWT.
     *
     * @param decodedJWT the decoded JWT from which to extract the role
     * @return the role associated with the token, or null if not found
     */
    String getTokenRole(@Nonnull DecodedJWT decodedJWT);
}

