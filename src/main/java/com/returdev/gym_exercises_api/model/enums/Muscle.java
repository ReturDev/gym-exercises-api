package com.returdev.gym_exercises_api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.gym_exercises_api.exceptions.InvalidEnumValueException;

import java.util.Arrays;
import java.util.List;

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
        try{
            return Muscle.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException ex){
            throw new InvalidEnumValueException(
                    value,
                    "validation.invalid_enum_value.message",
                    validValues()
            );
        }
    }

    private static String validValues(){
        List<String> names = Arrays.stream(Muscle.values())
                .map(Muscle::name)
                .toList();
        return String.join(", ", names.subList(0, names.size() - 1)) + " or " + names.get(names.size() - 1);
    }

}
