package com.returdev.gym_exercises_api.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;

/**
 * Data Transfer Object (DTO) for representing a token response.
 *
 * <p>
 * This record holds the authentication token and its expiration duration.
 * It also provides a method to get the formatted expiration time.
 * </p>
 *
 * @param authToken The authentication token.
 * @param expirationDuration The duration until the token expires.
 */
@Schema(name = "Token")
public record TokenResponseDTO(
        @JsonProperty("auth_token")
        String authToken,

        @JsonProperty("time_to_expire")
        Duration expirationDuration
) {

        /**
         * Returns a formatted string representing the expiration time in the format HH:mm:ss.
         *
         * @return A string representing the expiration duration formatted as HH:mm:ss.
         */
        @JsonGetter("time_to_expire")
        public String getFormattedExpirationTime() {
                return String.format(
                        "%02d:%02d:%02d",
                        expirationDuration.toHoursPart(),
                        expirationDuration.toMinutesPart(),
                        expirationDuration.toSecondsPart()
                );
        }
}

