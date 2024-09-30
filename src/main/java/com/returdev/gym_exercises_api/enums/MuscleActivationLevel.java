package com.returdev.gym_exercises_api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MuscleActivationLevel {

    HIGH,
    MEDIUM,
    LOW;

    @JsonCreator
    public static MuscleActivationLevel fromString(String value) throws IllegalArgumentException {
        return MuscleActivationLevel.valueOf(value.toUpperCase());
    }

}