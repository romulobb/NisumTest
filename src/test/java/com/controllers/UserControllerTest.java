package com.controllers;

import com.model.Phone;
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
    private UserService userService;
    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
        userController=new UserController();
        userController.setUserService(userService);

    }
    @Test
    public void testNewUserOK() {
        //Arrange
        UserInn userInn = new UserInn("userName","email@email.cl","Password12$",new Phone(12L,12,12345678));
        ResponseEntity responseExcepted = new ResponseEntity("User saved successfully", HttpStatus.OK);
        //Act
        ResponseEntity response = userController.newUser(userInn);
        //Assert
        assertEquals(response,responseExcepted);
    }


}