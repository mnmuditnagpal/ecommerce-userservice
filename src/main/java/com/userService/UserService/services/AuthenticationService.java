
package com.userService.UserService.services;

import com.userService.UserService.dtos.*;
import com.userService.UserService.exceptions.ValueAlreadyExistsException;
import com.userService.UserService.exceptions.AuthenticationFailedException;
import com.userService.UserService.exceptions.MaximumSessionLimitException;
import com.userService.UserService.models.Session;
import com.userService.UserService.models.SessionStatus;
import com.userService.UserService.models.User;
import com.userService.UserService.respositories.SessionRepository;
import com.userService.UserService.respositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SecretKey secretKey;

    @Autowired
    public AuthenticationService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        secretKey = Jwts.SIG.HS256.key().build();
    }

    public UserDto signUpUser(String email, String password) throws ValueAlreadyExistsException {

        //checking if email already exist or not
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            throw new ValueAlreadyExistsException("Email Already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return UserDto.from(user);
    }

    public ResponseEntity<UserDto> loginUser(String email, String password) throws AuthenticationFailedException,MaximumSessionLimitException {
        //Searching for user with the entered email
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new AuthenticationFailedException("Email Address Not Found");
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new AuthenticationFailedException("Password entered is wrong");
        }

        List<Session> allSessions = sessionRepository.findAllByUser(user);

        //Need to cap session to 2
        //throw new MaximumSessionLimitException("Reached Maximum Sessions Limit");


        //JwT Data
        Map<String,Object> jwtData = new HashMap<>();
        jwtData.put("email",email);
        jwtData.put("createdDate",new Date());
        jwtData.put("expiryDate",new Date(LocalDate.now().toEpochDay()));

        //generating java web token (jwt)
        String token = Jwts
                .builder()
                .claims(jwtData)
                .signWith(secretKey)
                .compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);

        //saving the session
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);

        MultiValueMap<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        //for storing returned token as headers and as a cookie. cookies are stored by browser and
        //are sent whenever we make a GET Request.
        headers.add(HttpHeaders.SET_COOKIE,"auth:"+token);

        return new ResponseEntity<>(userDto,headers, HttpStatus.OK);
    }

    public void logoutUser(String token) throws AuthenticationFailedException{
        Optional<Session> optionalSession = sessionRepository.findByToken(token);
        if(optionalSession.isEmpty()){
            throw new AuthenticationFailedException("Cannot logout user");
        }

        Session session = optionalSession.get();
        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);
    }

    public SessionStatus validateUser(String token){
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty()){
            return SessionStatus.ENDED;
        }

        Session session = sessionOptional.get();

        if(session.getSessionStatus().equals(SessionStatus.ENDED)){
            return SessionStatus.ENDED;
        }

        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        User user = session.getUser();
        if(!claimsJws.getPayload().get("email").equals(user.getEmail())){
            return SessionStatus.ENDED;
        }

//        System.out.println("DEBUG");
//
//        if(new Date().after((Date) claimsJws.getPayload().get("expiryDate"))){
//            return SessionStatus.ENDED;
//        }

        return SessionStatus.ACTIVE;
    }
}

