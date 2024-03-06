package com.userService.UserService.exceptions;

public class MaximumSessionLimitException extends Exception{
    public MaximumSessionLimitException(String errorMessage){
        super(errorMessage);
    }
}
