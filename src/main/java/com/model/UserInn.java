package com.model;

import lombok.Data;


@Data
public class UserInn {



    private String name;
    private String email;
    private String password;
    private Phone phone;



    // avoid this "No default constructor for entity"
    public UserInn() {
    }

    public UserInn(String name, String email, String password, Phone phone) {


        this.name = name;
        this.email = email;
        this.password = password;
        this.phone=phone;

    }

}
