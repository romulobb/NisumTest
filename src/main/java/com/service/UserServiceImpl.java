package com.service;

import com.error.InvalidMailException;
import com.error.UserCreatedException;
import com.error.UserPassInvalid;
import com.model.Phone;
import com.model.PhoneInn;
import com.model.User;
import com.model.UserInn;
import com.repositories.PhoneRepository;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private static final String SECRET_KEY = "your_secret_key"; // Use a strong secret key
    private static final long EXPIRATION_TIME = 600_000; // 10 minutes


    private String passwordPattern;

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPhoneRepository(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Value("${password.pattern.regexp}")
    public void setPasswordPattern(String passwordPattern){
        this.passwordPattern=passwordPattern;
    }



    public User saveUser(UserInn userInn) {

        User userSaved=null;


        if (!passValido(userInn.getPassword())){
            throw new UserPassInvalid();
        }
        if (userRepository.findByEmail(userInn.getEmail()) != null){
            throw new UserCreatedException(userInn.getEmail());
        }
        if (!mailValido(userInn.getEmail())){
            throw new InvalidMailException(userInn.getEmail());}
        else{
            String token=generateJWTToken(userInn.getName());
            userSaved  =new User(userInn.getName(),userInn.getEmail(),userInn.getPassword(),token);
            userRepository.save(userSaved);
            for(PhoneInn phone:userInn.getPhones()){
                Phone phoneSaved = new Phone(phone.getNumber(),phone.getCitycode(),phone.getCountrycode(),userInn.getEmail());
                phoneRepository.save(phoneSaved);
            }
        }




        return userSaved;
    }

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public Iterable<Phone> listAllPhones() {
        return phoneRepository.findAll();
    }

    public boolean mailValido(String mail){
          String regex = "^(.+)@(.+)$";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(mail);
          if  (matcher.matches()){
              return mail.split("\\.")[1].equalsIgnoreCase("cl");
          }  else return false;
    }
    public boolean passValido(String pass){

        Pattern pattern = Pattern.compile(passwordPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public String generateJWTToken(String username ) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String validateJWTToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}