package com.returdev.gym_exercises_api.repositories.security;


import com.returdev.gym_exercises_api.model.auth.AuthToken;

/**
 * Interface for managing authentication tokens.
 *
 * <p>
 * This repository provides methods to store, retrieve, and update authentication
 * tokens. It acts as an abstraction over the underlying token persistence mechanism.
 * </p>
 */
public interface TokenRepository {

    /**
     * Updates the stored authentication token with a new token.
     *
     * <p>
     * This method replaces the existing token with the provided {@link AuthToken}.
     * </p>
     *
     * @param newToken the new authentication token to store
     */
    void updateToken(AuthToken newToken);

    /**
     * Retrieves the currently stored authentication token.
     *
     * <p>
     * This method returns the token that is currently stored in the repository.
     * If no token is found, it may return null or throw an exception depending
     * on the implementation.
     * </p>
     *
     * @return the current {@link AuthToken}, or null if no token is stored
     */
    AuthToken getToken();

}
