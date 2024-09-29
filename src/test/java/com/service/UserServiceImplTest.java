package com.service;


import com.error.InvalidMailException;
import com.error.UserCreatedException;
import com.error.UserPassInvalid;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Phone;
import com.model.PhoneInn;
import com.model.User;
import com.model.UserInn;
import com.repositories.PhoneRepository;
import com.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserServiceImplTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Mock
    private TestRestTemplate restTemplate;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PhoneRepository phoneRepository;
    @Before
    public void init() {
         //   List<PhoneInn> phones = new ArrayList<>();
        //    phones.add(new PhoneInn(12L,12,12345678));
        //    UserInn userInn = new UserInn("userName","email@email.cl","Password12$",phones);
            User user = new User("UserName", "user@email.cl","Password12$","qwerwqerwqer");
            when(userRepository.findByEmail("user@email.cl")).thenReturn(user);
            MockitoAnnotations.initMocks(this);
            userService=new UserServiceImpl();
            userService.setUserRepository(userRepository);
            userService.setPhoneRepository(phoneRepository);
            userService.setPasswordPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
    }
    @Test
    public void testSetPasswordPattern() {
        //Arrange
        userService.setPasswordPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
        //Act
        boolean result=userService.passValido("Password12$");
        //Assert
        assertTrue(result);
    }
    @Test
    public void testSetPasswordPatternNegative() {
        //Arrange
        userService.setPasswordPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
        //Act
        boolean result=userService.passValido("Pass12$");
        //Assert
        assertFalse(result);
    }


    @Test
    public void testSaveUserOk() {
        // Arrange
        User user = new User("UserName", "user@email.cl","Password12$","qwerwqerwqer");
        when(userRepository.save(user)).thenReturn(user);
        // Act
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("UserName","user@email.cl","Password12$",phones);
        User retrievedUser = userService.saveUser(userInn);
        // Assert

        assertEquals(retrievedUser, user);
    }

    @Test
    public void testSaveUserNotOkMalformedMail() {
        // Arrange
        User user = new User("UserName", "user@email.cl","Password12$","qwerwqerwqer");
        when(userRepository.save(user)).thenReturn(user);
        // Act
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("UserName","useremail.cl","Password12$",phones);
        User retrievedUser=new User();
        try {
            retrievedUser = userService.saveUser(userInn);
        } catch (InvalidMailException e) {
        // Assert
            assertNotEquals(retrievedUser, user);
        }


    }

    @Test
    public void testSaveUserNotOkBadPassword() {
        // Arrange
        User user = new User("UserName", "user@email.cl","Password12$","qwerwqerwqer");
        when(userRepository.save(user)).thenReturn(user);
        // Act
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("UserName","user@email.cl","Pass12$",phones);
        User retrievedUser=new User();
        try {
            retrievedUser = userService.saveUser(userInn);
        } catch (UserPassInvalid e) {
            // Assert
            assertNotEquals(retrievedUser, user);
        }


    }
    @Test
    public void testSaveUserAlreadyCreated() {
        // Arrange
        User user = new User("UserName", "user@email.cl","Password12$","qwerwqerwqer");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        List<PhoneInn> phones = new ArrayList<>();
        phones.add(new PhoneInn(12L,12,12345678));
        UserInn userInn = new UserInn("UserName","user@email.cl","Password12$",phones);
        User retrievedUser=new User();
        try {
            retrievedUser = userService.saveUser(userInn);
        } catch (UserCreatedException e) {
            // Assert
            assertNotEquals(retrievedUser, user);
        }

    }

    @Test
    public void testMail() {
        //Arrange
        //Act
        boolean resultvalid=userService.mailValido("mail@email.cl");
        boolean resultinvalid1=userService.mailValido("mail@email.com");
        boolean resultinvalid2=userService.mailValido("mailemail.cl");
        //Assert
        assertTrue(resultvalid);
        assertFalse(resultinvalid1);
        assertFalse(resultinvalid2);

    }
    @Test
    public void testGenerateValidateToken() {
        //Arrange
        String userName="username";
        //Act
        String token=userService.generateJWTToken(userName);
        String userValidated=userService.validateJWTToken(token);
        //Assert
        assertTrue(userName.equalsIgnoreCase(userValidated));

    }
}