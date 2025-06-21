package com.waldhauser.tasklist.config.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), ((HttpStatus) status).name(), message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private record ErrorResponse (
        LocalDateTime timestamp,
        int status,
        String error,
        String message
    ) {
        public ErrorResponse(int status, String error, String message) {
            this(LocalDateTime.now(), status, error, message);
        }
    }

}