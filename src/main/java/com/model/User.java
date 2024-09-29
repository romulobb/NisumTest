package com.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Long number;
    private int citycode;
    private int countrycode;
    private String token;
    private Date created;
    private Date lastLoguin;
    private boolean isActive;



    // avoid this "No default constructor for entity"
    public User() {
    }

    public User(String name, String email, String password,Phone phone,String token) {
        UUID uuid=UUID.randomUUID();
        this.id = uuid.toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = phone.getNumber();
        this.citycode = phone.getCitycode();
        this.countrycode = phone.getCountrycode();
        this.created=new Date();
        this.lastLoguin=new Date();
        this.token=token;
        this.isActive=true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}
