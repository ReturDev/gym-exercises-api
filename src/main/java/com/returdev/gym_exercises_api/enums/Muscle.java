package com.returdev.gym_exercises_api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @JsonCreator
    public static Muscle fromString(String value) throws IllegalArgumentException {
        return Muscle.valueOf(value.toUpperCase());
    }

}
