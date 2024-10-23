package com.returdev.gym_exercises_api.advice;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.returdev.gym_exercises_api.dto.response.error.ErrorResponseDTO;
import com.returdev.gym_exercises_api.exceptions.InsufficientPermissionsException;
import com.returdev.gym_exercises_api.manager.message.MessageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler for security-related exceptions.
 * <p>
 * This class is responsible for handling exceptions that occur during
 * security checks, such as JWT verification failures, insufficient
 * permissions, and insufficient authentication. It uses Spring's
 * @RestControllerAdvice to provide a unified response format for these
 * types of exceptions, ensuring appropriate HTTP status codes and messages.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class SecurityExceptionHandler {

    private final MessageManager messageManager;

    /**
     * Handles JWTVerificationException.
     * <p>
     * This method is triggered when a JWT verification fails.
     * It returns a ProblemDetail response with a 401 (Unauthorized) status
     * and the error message from the exception.
     *
     * @param ex the JWTVerificationException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleJWTVerificationException(JWTVerificationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * Handles InsufficientPermissionsException.
     * <p>
     * This method is triggered when a user attempts to perform an action
     * without the necessary permissions. It returns an ErrorResponseDTO
     * response with a 403 (Forbidden) status and a standardized message
     * indicating insufficient permissions.
     *
     * @param ex the InsufficientPermissionsException that was thrown
     * @return an ErrorResponseDTO object containing the status and error message
     */
    @ExceptionHandler(InsufficientPermissionsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleInsufficientPermissionsException(InsufficientPermissionsException ex) {
        return ErrorResponseDTO.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                messageManager.getMessage("exception.insufficient.permissions")
        );
    }

    /**
     * Handles InsufficientAuthenticationException.
     * <p>
     * This method is triggered when a user is not authenticated
     * properly. It returns an ErrorResponseDTO response with a
     * 401 (Unauthorized) status and a standardized message
     * indicating insufficient authentication.
     *
     * @param ex the InsufficientAuthenticationException that was thrown
     * @return an ErrorResponseDTO object containing the status and error message
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ErrorResponseDTO.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                messageManager.getMessage("exception.insufficient.authentication")
        );
    }
}

