package com.returdev.gym_exercises_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception thrown when an invalid enum value is provided.
 *
 * <p>
 * This exception carries information about the invalid value, the
 * resource message key for localization, and the list of valid values.
 * It extends RuntimeException, allowing it to be thrown without
 * being explicitly declared in method signatures.
 * </p>
 */
@Getter
@AllArgsConstructor
public class InvalidEnumValueException extends RuntimeException {

    private final String invalidValue;   // The invalid enum value that was provided
    private final String messageResource; // Resource message key for localization
    private final String validValues;     // Comma-separated list of valid enum values

    /**
     * {@inheritDoc}
     *
     * @return A formatted message detailing the invalid value, expected values, and message code.
     */
    @Override
    public String getMessage() {
        return String.format("Invalid value: '%s'. Expected one of: [%s]. Message code: %s",
                invalidValue, validValues, messageResource);
    }
}

