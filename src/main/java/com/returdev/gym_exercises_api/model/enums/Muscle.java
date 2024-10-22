package com.returdev.gym_exercises_api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.gym_exercises_api.exceptions.InvalidEnumValueException;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing different muscle groups.
 *
 * <p>
 * This enum provides constants for various muscle groups used in exercises.
 * It also includes a method for converting a string representation into a corresponding enum constant.
 * </p>
 */
public enum Muscle {

    UPPER_CHEST,
    LOWER_CHEST,
    INNER_CHEST,

    DORSALS,
    WIDE_BACK,
    RHOMBOIDS,
    TRAPEZES,
    LUMBAR,

    ANTERIOR_DELTOID,
    LATERAL_DELTOID,
    POSTERIOR_DELTOID,

    QUADRICEPS,
    HAMSTRINGS,
    CALVES,
    BUTTOCKS,

    BICEPS,
    TRICEPS,
    FOREARMS,

    ABS,
    OBLIQUES;

    /**
     * Converts a string value to its corresponding {@link Muscle} enum constant.
     *
     * <p>
     * The string comparison is case-insensitive. If the provided string does not match any
     * of the enum constants, an {@link InvalidEnumValueException} is thrown.
     * </p>
     *
     * @param value the string representation of the muscle group
     * @return the corresponding {@link Muscle} enum constant
     * @throws IllegalArgumentException if the string value does not match any enum constant
     */
    @JsonCreator
    public static Muscle fromString(String value) throws IllegalArgumentException {
        try {
            return Muscle.valueOf(value.toUpperCase());
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
     * This method constructs a string listing all valid {@link Muscle} names, formatted
     * for display in error messages.
     * </p>
     *
     * @return a comma-separated list of valid muscle names
     */
    private static String validValues() {
        List<String> names = Arrays.stream(Muscle.values())
                .map(Muscle::name)
                .toList();
        return String.join(", ", names.subList(0, names.size() - 1)) + " or " + names.get(names.size() - 1);
    }
}

