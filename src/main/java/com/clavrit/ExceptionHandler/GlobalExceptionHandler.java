package com.clavrit.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.clavrit.Enums.ApiStatus;
import com.clavrit.response.ApisResponse;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApisResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ApisResponse response = new ApisResponse(ApiStatus.NOT_FOUND, "Resource not found", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApisResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ApisResponse response = new ApisResponse(ApiStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApisResponse> handleGenericException(Exception ex) {
        ApisResponse response = new ApisResponse(ApiStatus.INTERNAL_ERROR, "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
