package com.hireconnect.presentation.exception;

import com.hireconnect.core.exception.AuthenticationException;
import com.hireconnect.core.exception.EmailAlreadyExistsException;
import com.hireconnect.presentation.controller.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        List<String> errors = List.of("Email is already registered.", "Please use a different email address.");
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), errors);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), List.of("Email or password is incorrect."));
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
