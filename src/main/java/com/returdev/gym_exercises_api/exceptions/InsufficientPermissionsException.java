package com.returdev.gym_exercises_api.exceptions;

import lombok.NoArgsConstructor;

/**
 * Exception thrown when a user attempts to perform an action without the necessary permissions.
 * <p>
 * This runtime exception indicates that the user does not have sufficient rights
 * to access a resource or execute a specific operation.
 */
@NoArgsConstructor
public class InsufficientPermissionsException extends RuntimeException {

    /**
     * Constructs a new InsufficientPermissionsException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}

