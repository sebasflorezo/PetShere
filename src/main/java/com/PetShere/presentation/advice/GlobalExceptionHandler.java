package com.PetShere.presentation.advice;

import com.PetShere.presentation.dto.error.ErrorDetails;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.NotFoundException;
import com.PetShere.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getTypeName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getTypeName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_GATEWAY
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getTypeName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getTypeName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<ErrorDetails>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .errorType(exception.getClass().getTypeName())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
