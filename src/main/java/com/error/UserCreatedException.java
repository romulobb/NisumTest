package com.error;

public class UserCreatedException extends RuntimeException {

    public UserCreatedException(String email) {
        super("User email register in the database : " + email);
    }

}
