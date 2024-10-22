package com.returdev.gym_exercises_api.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.util.Map;


/**
 * Data Transfer Object (DTO) for representing error responses.
 * <p>
 * This class extends {@link ProblemDetail} to provide additional context for error responses
 * in the API. It includes a map for more detailed error information that can be serialized to JSON.
 * </p>
 *
 * <p>
 * The {@code moreDetails} property is included in the response only if it is not null,
 * allowing for flexible error reporting.
 * </p>
 *
 * <p>
 * Instances of {@link ErrorResponseDTO} can be created using the following methods:
 * <ul>
 *   <li>{@link ErrorResponseDTO#forStatus(int)}</li>
 *   <li>{@link ErrorResponseDTO#forStatus(HttpStatusCode)}</li>
 *   <li>{@link ErrorResponseDTO#forStatusAndDetail(HttpStatusCode, String)}</li>
 * </ul>
 * These methods provide various ways to specify the HTTP status and detail message for the error response.
 * </p>
 */
public class ErrorResponseDTO extends ProblemDetail {

    /**
     * Retrieves the properties of the error response, which includes more detailed error information.
     *
     * @return a map containing the error properties, including more details if any.
     */
    @Schema(
            implementation = Map.class
    )
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "moreDetails")  
    @Override
    public Map<String, Object> getProperties() {
        return super.getProperties();
    }

    /**
     * Private constructor to prevent instantiation of this class.
     * This class is intended to be used as a response DTO.
     */
    private ErrorResponseDTO() {}
}

