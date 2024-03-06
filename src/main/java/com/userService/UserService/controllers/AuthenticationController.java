package com.userService.UserService.controllers;

import com.userService.UserService.dtos.*;
import com.userService.UserService.exceptions.ValueAlreadyExistsException;
import com.userService.UserService.exceptions.AuthenticationFailedException;
import com.userService.UserService.exceptions.MaximumSessionLimitException;
import com.userService.UserService.models.SessionStatus;
import com.userService.UserService.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody UserSignUpDto userSignUpDto) throws ValueAlreadyExistsException {
        UserDto userDto = authenticationService.signUpUser(userSignUpDto.getEmail(),userSignUpDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) throws AuthenticationFailedException, MaximumSessionLimitException {
        return authenticationService.loginUser(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestBody UserLogoutRequestDto userLogoutRequestDto) throws AuthenticationFailedException{
         authenticationService.logoutUser(userLogoutRequestDto.getToken());
         return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateUser(@RequestBody ValidateTokenDto validateTokenDto){
        //System.out.println(token);
        SessionStatus sessionStatus = authenticationService.validateUser(validateTokenDto.getToken());
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }

}
