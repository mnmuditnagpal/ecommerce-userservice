package com.userService.UserService.dtos;

import com.userService.UserService.models.Role;
import com.userService.UserService.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles;
    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
