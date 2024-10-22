package com.returdev.gym_exercises_api.repositories.security;

import com.returdev.gym_exercises_api.model.auth.AuthToken;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link TokenRepository} for managing in-memory authentication tokens.
 *
 * <p>
 * This class stores the current authentication token in memory and provides methods to
 * update and retrieve it. Since the token is stored in-memory, it will be lost if the
 * application is restarted.
 * </p>
 */
@Repository
@NoArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    /**
     * The current authentication token stored in memory.
     */
    private AuthToken currentToken;

    /**
     * {@inheritDoc}
     *
     * <p>
     * This method updates the in-memory token by replacing the existing one with the provided token.
     * </p>
     *
     * @param newToken the new authentication token to store
     */
    @Override
    public void updateToken(AuthToken newToken) {
        currentToken = newToken;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns the currently stored in-memory authentication token. If no token is stored,
     * it returns {@code null}.
     * </p>
     *
     * @return the current {@link AuthToken}, or {@code null} if no token is stored
     */
    @Override
    public AuthToken getToken() {
        return currentToken;
    }

}

