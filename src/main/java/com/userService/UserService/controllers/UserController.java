package com.userService.UserService.controllers;

import com.userService.UserService.dtos.SetUserRolesDto;
import com.userService.UserService.dtos.UserDto;
import com.userService.UserService.exceptions.NotFoundException;
import com.userService.UserService.models.User;
import com.userService.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") String id) throws NotFoundException {
        UserDto userDto = userService.getUserDetails(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") String id, @RequestBody SetUserRolesDto setUserRolesDto) throws NotFoundException {
        UserDto userDto = userService.setUserRoles(id,setUserRolesDto.getRoleIds());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
