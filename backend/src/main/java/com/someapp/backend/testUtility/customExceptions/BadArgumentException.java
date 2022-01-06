package com.someapp.backend.testUtility.customExceptions;

public class BadArgumentException extends RuntimeException {
    public BadArgumentException(String message) {
        super(message);
    }
}