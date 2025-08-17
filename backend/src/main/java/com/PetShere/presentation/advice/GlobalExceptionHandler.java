package com.PetShere.presentation.advice;

import com.PetShere.presentation.dto.error.ErrorDetails;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.NotFoundException;
import com.PetShere.service.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> buildErrorResponse(Exception exception, HttpStatus status) {
        return buildErrorResponse(exception, status, exception.getMessage());
    }

    private ResponseEntity<ErrorDetails> buildErrorResponse(Exception exception, HttpStatus status, String message) {
        ErrorDetails details = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorType(exception.getClass().getSimpleName())
                .message(message)
                .build();

        return new ResponseEntity<>(details, status);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleArgumentException(IllegalArgumentException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(MessagingException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return buildErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleMailAuthenticationException(MailAuthenticationException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_GATEWAY);
    }
}
