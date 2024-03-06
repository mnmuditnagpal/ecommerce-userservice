package com.userService.UserService.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationFailedException extends  Exception{
    public AuthenticationFailedException(String errorMessage){
        super(errorMessage);
    }
}
