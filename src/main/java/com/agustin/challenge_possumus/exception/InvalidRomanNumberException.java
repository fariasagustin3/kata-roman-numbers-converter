package com.agustin.challenge_possumus.exception;

public class InvalidRomanNumberException extends RuntimeException {
    public InvalidRomanNumberException(String message) {
        super(message);
    }
    public InvalidRomanNumberException(String message, Throwable cause) { super(message, cause); }
}
