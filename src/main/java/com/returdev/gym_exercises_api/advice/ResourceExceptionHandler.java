package com.returdev.gym_exercises_api.advice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Handler for resource-related exceptions.
 * <p>
 * This class is responsible for handling exceptions related to resources,
 * such as when an entity is not found or when there is a conflict due to
 * an existing entity. It uses Spring's @RestControllerAdvice to provide
 * a unified response format for these types of exceptions.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResourceExceptionHandler {

    /**
     * Handles the EntityNotFoundException.
     * <p>
     * This method returns a ProblemDetail response with a 404 (Not Found) status
     * and the message from the exception. It is triggered when a requested
     * entity does not exist in the database.
     *
     * @param ex the EntityNotFoundException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles the EntityExistsException.
     * <p>
     * This method returns a ProblemDetail response with a 409 (Conflict) status
     * and the message from the exception. It is triggered when an attempt is made
     * to create an entity that already exists in the database.
     *
     * @param ex the EntityExistsException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleEntityExistsException(EntityExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Handles the NoResourceFoundException.
     * <p>
     * This method returns a ProblemDetail response with a 404 (Not Found) status
     * and the message from the exception. It is triggered when a requested resource
     * cannot be found.
     *
     * @param ex the NoResourceFoundException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

}

