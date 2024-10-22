package com.returdev.gym_exercises_api.service.security;

import com.returdev.gym_exercises_api.model.auth.AuthToken;

/**
 * Service interface for handling token-related operations.
 *
 * <p>
 * This interface defines the contract for generating or retrieving an authentication token.
 * Implementations of this interface should provide the logic to return a valid {@link AuthToken},
 * which can be used for authenticating users or securing API requests.
 * </p>
 */
public interface TokenService {

    /**
     * Retrieves or generates an authentication token.
     *
     * @return an {@link AuthToken} representing the current authentication token.
     */
    AuthToken getToken();

}

