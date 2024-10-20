package com.returdev.gym_exercises_api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.gym_exercises_api.exceptions.InvalidEnumValueException;

import java.util.Arrays;
import java.util.List;

public enum MuscleActivationLevel {

    HIGH,
    MEDIUM,
    LOW;

    @JsonCreator
    public static MuscleActivationLevel fromString(String value) throws IllegalArgumentException {
        try{
            return MuscleActivationLevel.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException ex){
            throw new InvalidEnumValueException(
                    value,
                    "validation.invalid_enum_value.message",
                    validValues()
            );
        }
    }

    private static String validValues(){
        List<String> names = Arrays.stream(MuscleActivationLevel.values())
                .map(MuscleActivationLevel::name)
                .toList();
        return String.join(", ", names.subList(0, names.size() - 1)) + " or " + names.get(names.size() - 1);
    }

}