package com.userService.UserService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogoutRequestDto {
    //private String userID;
    private String token;
}
