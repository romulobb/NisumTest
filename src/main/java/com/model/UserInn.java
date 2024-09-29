package com.model;

import lombok.Data;

import java.util.List;


@Data
public class UserInn {



    private String name;
    private String email;
    private String password;
    private List<PhoneInn> phones;



    // avoid this "No default constructor for entity"
    public UserInn() {
    }

    public UserInn(String name, String email, String password, List<PhoneInn> phones) {


        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;

    }

}
