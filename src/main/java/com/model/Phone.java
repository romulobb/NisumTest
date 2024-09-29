package com.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Phone {
    @Id
    private String id;
    private Long number;
    private int citycode;
    private int countrycode;
    private String email;

    public Phone() {
    }

    public Phone(Long number, int citycode, int countrycode,String email) {
        UUID uuid=UUID.randomUUID();
        this.id = uuid.toString();
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
        this.email=email;

    }

}