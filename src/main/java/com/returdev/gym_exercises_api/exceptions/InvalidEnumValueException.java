package com.returdev.gym_exercises_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidEnumValueException extends RuntimeException {

    private final String invalidValue;
    private final String messageResource;
    private final String validValues;

    @Override
    public String getMessage() {
        return String.format("Invalid value: '%s'. Expected one of: [%s]. Message code: %s",
                invalidValue, validValues, messageResource);
    }
}
