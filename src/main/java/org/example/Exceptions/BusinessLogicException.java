package org.example.Exceptions;

public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
