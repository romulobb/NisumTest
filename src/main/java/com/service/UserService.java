package com.service;

import com.model.Phone;
import com.model.User;
import com.model.UserInn;


public interface UserService {

    User saveUser(UserInn user) ;

    Iterable<User> listAllUsers();

    Iterable<Phone> listAllPhones();

    boolean mailValido(String mail);

    boolean passValido(String pass);

    String generateJWTToken(String username );

    String validateJWTToken(String token);


}
