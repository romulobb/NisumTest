package com.controllers;

import com.error.InvalidMailException;
import com.error.UserCreatedException;
import com.error.UserPassInvalid;
import com.model.User;
import com.model.UserInn;
import com.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(value="", description="Register User")
public class UserController {



    private UserService service;

    @Autowired
    public void setUserService(UserService userService) {
        this.service = userService;
    }


    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity newUser(@RequestBody UserInn newUser) {
        try {
            User usuario = service.saveUser(newUser);
            return new ResponseEntity("User saved successfully", HttpStatus.OK);
        } catch (UserCreatedException | InvalidMailException |UserPassInvalid e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<User> list(){

        return  service.listAllUsers();
    }


}
