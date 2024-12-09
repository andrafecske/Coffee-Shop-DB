package org.example.Exceptions;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
