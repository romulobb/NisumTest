package com.controllers;

import com.error.InvalidMailException;
import com.error.UserCreatedException;
import com.error.UserPassInvalid;
import com.model.Phone;
import com.model.PhoneInn;
import com.model.User;
import com.model.UserInn;
import com.service.UserService;
import com.service.UserServiceImpl;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest extends TestCase {

    @Mock
    private TestRestTemplate restTemplate;
    @Mock
    private UserController userController;
    @Mock
    private UserServiceImpl userService;
    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
        userController=new UserController();
        userController.setUserService(userService);

    }
    @Test
    public void testNewUserOK() {
        //Arrange
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("userName","email@email.cl","Password12$",phones);
        ResponseEntity responseExcepted = new ResponseEntity("User saved successfully", HttpStatus.OK);
        //Act
        ResponseEntity response = userController.newUser(userInn);
        //Assert
        assertEquals(response,responseExcepted);
    }

    @Test
    public void testNewUserNotOKInvalidEmail() {
        //Arrange
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("userName","email@email.","Password12$",phones);
        when(userService.saveUser(userInn)).thenThrow(new InvalidMailException(userInn.getEmail()));
        ResponseEntity responseExcepted = new ResponseEntity("Invalid Mail: email@email.", HttpStatus.FORBIDDEN);
        //Act
        ResponseEntity response = userController.newUser(userInn);
        //Assert
        assertEquals(response,responseExcepted);
    }

    @Test
    public void testNewUserNotOKEmailAlreadyExist() {
        //Arrange
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("userName","email@email.cl","Password12$",phones);
        when(userService.saveUser(userInn)).thenThrow(new UserCreatedException(userInn.getEmail()));
        ResponseEntity responseExcepted = new ResponseEntity("User email register in the database : email@email.cl", HttpStatus.FORBIDDEN);
        //Act
        ResponseEntity response = userController.newUser(userInn);
        //Assert
        assertEquals(response,responseExcepted);
    }
    @Test
    public void testNewUserNotOKUserPassInvalid() {
        //Arrange
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("userName","email@email.cl","Pass2$",phones);
        when(userService.saveUser(userInn)).thenThrow(new UserPassInvalid());
        ResponseEntity responseExcepted = new ResponseEntity("User password invalid format", HttpStatus.FORBIDDEN);
        //Act
        ResponseEntity response = userController.newUser(userInn);
        //Assert
        assertEquals(response,responseExcepted);
    }
}