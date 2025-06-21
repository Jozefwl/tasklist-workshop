package com.waldhauser.tasklist.config.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * RestResponseEntityExceptionHandler is a global exception handler for REST APIs, extending
 * Spring's ResponseEntityExceptionHandler. It provides a centralized way to handle and format
 * exceptions, specifically for cases where method arguments fail validation.
 * <p>
 * Responsibilities:
 * - Overrides and customizes the behavior of the handleMethodArgumentNotValid method
 *   to handle MethodArgumentNotValidException, which is thrown when a request's validation fails.
 * - Constructs a response payload containing error details, including a timestamp, HTTP status code,
 *   error type, and validation failure message, and returns it with the appropriate HTTP status.
 * <p>
 * Thread Safety:
 * - This class is stateless and thread-safe, relying on Spring's built-in exception handling mechanisms.
 * <p>
 * ErrorResponse:
 * - A record class encapsulating error details, including the timestamp, HTTP status, error type,
 *   and error message.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles `MethodArgumentNotValidException` thrown when method arguments fail validation.
     * This method constructs a custom error response containing the timestamp,
     * status code, error type, and validation failure message, and returns it
     * with a `BAD_REQUEST` HTTP status.
     *
     * @param ex the exception containing details about the validation errors
     * @param headers the HTTP headers to be sent with the response
     * @param status the HTTP status code to be sent with the response
     * @param request the current web request context
     * @return a `ResponseEntity` containing the error response and the `BAD_REQUEST` status
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), ((HttpStatus) status).name(), message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * ErrorResponse is a record that encapsulates error details intended for use in
     * error responses in a REST API. It provides a standardized way to represent
     * and return error information to clients when exceptions or validation failures occur.
     * <p>
     * Properties:
     * - timestamp: The time at which the error occurred.
     * - status: The HTTP status code associated with the error response.
     * - error: A short description or classification of the error type.
     * - message: A detailed message describing the error.
     * <p>
     * Constructors:
     * - A primary constructor accepts the `timestamp`, `status`, `error`, and `message`
     *   parameters to provide complete flexibility when creating an error response.
     * - A secondary constructor omits the `timestamp` parameter and automatically sets it
     *   to the current time using `LocalDateTime.now()`.
     * <p>
     * Use Case:
     * - This record is typically constructed and returned as part of error handling mechanisms
     *   in REST APIs, ensuring consistent and informative responses to clients.
     * <p>
     * Immutability:
     * - The record is immutable, providing thread safety and promoting best practices
     *   for handling error responses in concurrent environments.
     */
    private record ErrorResponse (
        LocalDateTime timestamp,
        int status,
        String error,
        String message
    ) {
        /**
         * Constructs an instance of the ErrorResponse record with the provided HTTP status,
         * error description, and error message. The timestamp is automatically set to the
         * current time when this constructor is invoked.
         *
         * @param status the HTTP status code associated with this error response
         * @param error a short description or classification of the error type
         * @param message a detailed message providing additional context about the error
         */
        public ErrorResponse(int status, String error, String message) {
            this(LocalDateTime.now(), status, error, message);
        }
    }

}