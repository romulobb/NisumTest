package com.error;

public class InvalidMailException extends RuntimeException {

    public InvalidMailException(String email) {
        super("Invalid Mail: " + email);
    }

}
