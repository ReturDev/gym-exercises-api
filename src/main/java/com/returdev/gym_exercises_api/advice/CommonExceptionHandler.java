package com.returdev.gym_exercises_api.advice;

import com.returdev.gym_exercises_api.dto.response.error.ErrorResponseDTO;
import com.returdev.gym_exercises_api.manager.message.MessageManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions that occur throughout the application,
 * providing a unified response format for error messages and logging errors.
 * It uses Spring's @RestControllerAdvice to intercept exceptions thrown by
 * controller methods and respond with appropriate HTTP status codes and messages.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CommonExceptionHandler {

    private final MessageManager messageManager;

    /**
     * Handles all exceptions that are not explicitly caught by other handlers.
     * <p>
     * This method logs the error message and stack trace for internal server errors
     * and returns a ProblemDetail object with a generic error message.
     * The response will have a status of 500 (Internal Server Error).
     *
     * @param ex the exception that was thrown
     * @return a ProblemDetail object containing the error status and message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleException(Exception ex) {
        // Log the error with stack trace for debugging purposes
        log.error("Internal Server Error", ex);

        // Return a ProblemDetail response with a generic server error message
        return ErrorResponseDTO.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageManager.getMessage("exception.generic.server_internal_error")
        );
    }
}

