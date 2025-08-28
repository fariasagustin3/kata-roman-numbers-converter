package com.agustin.challenge_possumus.exception;

public class InvalidArabicNumberException extends RuntimeException {
    public InvalidArabicNumberException(String message) {
        super(message);
    }
    public InvalidArabicNumberException(String message, Throwable cause) { super(message, cause); }
}
