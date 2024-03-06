package com.userService.UserService.exceptions;

import com.userService.UserService.exceptions.exceptionDto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//for telling controller to handle exceptions here
public class GlobalExceptionHandler {
    @ExceptionHandler(ValueAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleEmailUsernameAlreadyExistsException(
            ValueAlreadyExistsException valueAlreadyExistsException
    ){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST, valueAlreadyExistsException.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(
            AuthenticationFailedException authenticationFailedException
    ){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, authenticationFailedException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaximumSessionLimitException.class)
    public ResponseEntity<ExceptionDto> handleMaximumLimitException(
            MaximumSessionLimitException maximumSessionLimitException
    ){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST,maximumSessionLimitException.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND,notFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }
}
