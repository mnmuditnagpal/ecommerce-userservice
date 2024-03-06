package com.userService.UserService.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception{
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}
