package com.joach27.urltoshort.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Record for error response
record ErrorResponse(LocalDateTime timestamp, int status, String error, String message){}

@RestControllerAdvice 
public class GlobalExceptionHandler{

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUrlException(InvalidUrlException ex){
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(), 
            "Bad Request", 
            ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (UrlNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.NOT_FOUND.value(), 
            "Not Found", 
            ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (SlugNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSlugNotFoundException(SlugNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.NOT_FOUND.value(), 
            "Not Found", 
            ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex){
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            500, 
            "Internal Eerver Error", 
            ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}