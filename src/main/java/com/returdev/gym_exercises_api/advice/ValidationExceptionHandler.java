package com.returdev.gym_exercises_api.advice;

import com.returdev.gym_exercises_api.exceptions.InvalidEnumValueException;
import com.returdev.gym_exercises_api.manager.message.MessageManager;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for validation-related exceptions.
 * <p>
 * This class is responsible for handling exceptions that occur during
 * the validation of request parameters or body content. It uses Spring's @RestControllerAdvice
 * to provide a unified response format for these
 * types of exceptions, specifically for validation failures.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {

    private final MessageManager messageManager;

    /**
     * Handles MethodArgumentNotValidException.
     * <p>
     * This method is triggered when a method argument annotated with
     * validation constraints fails validation. It gathers field error messages
     * and returns a ProblemDetail response with a 400 (Bad Request) status.
     *
     * @param ex the MethodArgumentNotValidException that was thrown
     * @return a ProblemDetail object containing the status and error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();

        // Gather field errors from the validation exception
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        return getErrorsProblemDetail(errorMessages);
    }

    /**
     * Handles ConstraintViolationException.
     * <p>
     * This method is triggered when a constraint violation occurs on a
     * method parameter. It collects the violations and returns a
     * ProblemDetail response with a 400 (Bad Request) status.
     *
     * @param ex the ConstraintViolationException that was thrown
     * @return a ProblemDetail object containing the status and error messages
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMessages = new HashMap<>();

        // Collect constraint violations into error messages
        ex.getConstraintViolations().forEach(violation -> {
            String fieldPath = violation.getPropertyPath().toString();
            errorMessages.put(
                    fieldPath.substring(fieldPath.lastIndexOf('.') + 1),
                    violation.getMessage()
            );
        });

        return getErrorsProblemDetail(errorMessages);
    }

    /**
     * Handles InvalidEnumValueException.
     * <p>
     * This method is triggered when an invalid value is passed for an
     * enumeration type. It returns a ProblemDetail response with a
     * 400 (Bad Request) status and a message indicating the invalid
     * value and valid options.
     *
     * @param ex the InvalidEnumValueException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(InvalidEnumValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleInvalidEnumValueException(InvalidEnumValueException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessageWithParams(
                        ex.getMessageResource(),
                        new String[]{
                                ex.getInvalidValue(),
                                ex.getValidValues()
                        }
                ));
    }

    /**
     * Handles IllegalArgumentException.
     * <p>
     * This method is triggered when an illegal argument is passed to a method.
     * It returns a ProblemDetail response with a 400 (Bad Request) status
     * and the exception's message.
     *
     * @param ex the IllegalArgumentException that was thrown
     * @return a ProblemDetail object containing the status and error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    /**
     * Handles exceptions thrown when a method argument type mismatch occurs.
     * <p>
     * This exception is triggered when a method is called with an argument
     * that does not match the expected type, such as when a request parameter
     * cannot be converted to the specified type. The handler responds
     * with a 400 BAD REQUEST status and provides a detailed error message.
     * <p>
     *
     * @param ex the {@link MethodArgumentTypeMismatchException} that was thrown
     * @return a {@link ProblemDetail} object containing the status and error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        Class<?> requiredType = ex.getRequiredType();
        String requiredTypeName = requiredType != null ? requiredType.getSimpleName() : "Unspecified";
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessageWithParams(
                        "exception.generic.type_mismatch",
                        new String[]{requiredTypeName}
                )
        );
    }

    /**
     * Handles exceptions thrown when an HTTP message is not readable.
     * <p>
     * This exception is triggered when the request body cannot be deserialized
     * due to malformed JSON or other issues that prevent the server from interpreting
     * the incoming request. The handler responds with a 400 BAD REQUEST status
     * and provides a detailed error message indicating the nature of the problem.
     * </p>
     *
     * @param ex the {@link HttpMessageNotReadableException} that was thrown
     * @return a {@link ProblemDetail} object containing the status and error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage("exception.json.request_malformed")
        );
    }

    /**
     * Creates a ProblemDetail object for validation errors.
     * <p>
     * This method constructs a ProblemDetail response containing the status
     * and a map of validation error messages.
     *
     * @param errorMessages a map of field names to error messages
     * @return a ProblemDetail object containing the status and error messages
     */
    private ProblemDetail getErrorsProblemDetail(Map<String, String> errorMessages) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage("validation.detail.failed.message")
        );

        // Set the property for errors
        problemDetail.setProperty("errors", errorMessages);

        return problemDetail;
    }

}

