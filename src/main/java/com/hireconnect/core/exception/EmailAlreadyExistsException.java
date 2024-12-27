package com.hireconnect.core.exception;

public class EmailAlreadyExistsException extends  RuntimeException {
    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
