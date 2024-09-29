package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class PhoneInn {

    @Id
    @GeneratedValue
    private Long number;
    private int citycode;
    private int countrycode;


    public PhoneInn(Long number, int citycode, int countrycode) {

        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;

    }

}