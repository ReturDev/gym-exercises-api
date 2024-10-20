package com.returdev.gym_exercises_api.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;


public record TokenResponseDTO(
        String authToken,
        @JsonProperty("time_to_expire")
        Duration expirationDuration
) {

        @JsonGetter("time_to_expire")
        public String getFormattedExpirationTime(){
                return String.format(
                        "%02d:%02d:%02d",
                        expirationDuration.toHoursPart(),
                        expirationDuration.toMinutesPart(),
                        expirationDuration.toSecondsPart()
                );
        }

}
