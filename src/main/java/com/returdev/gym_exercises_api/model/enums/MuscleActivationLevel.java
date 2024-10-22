package com.returdev.gym_exercises_api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.gym_exercises_api.exceptions.InvalidEnumValueException;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing different levels of muscle activation.
 *
 * <p>
 * This enum defines the activation levels for muscles that can be used in various exercises.
 * The activation levels indicate the intensity of muscle engagement during a workout.
 * </p>
 */
public enum MuscleActivationLevel {

    HIGH,
    MEDIUM,
    LOW;

    /**
     * Converts a string value to its corresponding {@link MuscleActivationLevel} enum constant.
     *
     * <p>
     * The string comparison is case-insensitive. If the provided string does not match any
     * of the enum constants, an {@link InvalidEnumValueException} is thrown.
     * </p>
     *
     * @param value the string representation of the muscle activation level
     * @return the corresponding {@link MuscleActivationLevel} enum constant
     * @throws IllegalArgumentException if the string value does not match any enum constant
     */
    @JsonCreator
    public static MuscleActivationLevel fromString(String value) throws IllegalArgumentException {
        try {
            return MuscleActivationLevel.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(
                    value,
                    "validation.invalid_enum_value.message",
                    validValues()
            );
        }
    }

    /**
     * Returns a string of valid enum constant names for error messaging.
     *
     * <p>
     * This method constructs a string listing all valid {@link MuscleActivationLevel} names, formatted
     * for display in error messages.
     * </p>
     *
     * @return a comma-separated list of valid activation level names
     */
    private static String validValues() {
        List<String> names = Arrays.stream(MuscleActivationLevel.values())
                .map(MuscleActivationLevel::name)
                .toList();
        return String.join(", ", names.subList(0, names.size() - 1)) + " or " + names.get(names.size() - 1);
    }
}
