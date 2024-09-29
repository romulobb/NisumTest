package com.error;

public class UserPassInvalid extends RuntimeException{

        public UserPassInvalid() {
            super("User password invalid format");
        }


}
