package com.userService.UserService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SetUserRolesDto {
    private List<String> roleIds;
}
