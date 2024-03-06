package com.userService.UserService.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueAlreadyExistsException extends Exception{
    public ValueAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
