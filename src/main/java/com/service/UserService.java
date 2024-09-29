package com.service;

import com.model.User;
import com.model.UserInn;


public interface UserService {

    User saveUser(UserInn user) ;

    Iterable<User> listAllUsers();
}
