package com.userService.UserService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private String email;
    private String password;
}
