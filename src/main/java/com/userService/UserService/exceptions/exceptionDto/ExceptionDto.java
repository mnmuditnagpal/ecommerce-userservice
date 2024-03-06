package com.userService.UserService.exceptions.exceptionDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDto {
    private HttpStatus httpStatus;
    private String errorMessage;

    public ExceptionDto(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
