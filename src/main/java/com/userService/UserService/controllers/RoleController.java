package com.userService.UserService.controllers;

import com.userService.UserService.dtos.CreateRoleRequestDto;
import com.userService.UserService.exceptions.ValueAlreadyExistsException;
import com.userService.UserService.models.Role;
import com.userService.UserService.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) throws ValueAlreadyExistsException {
        Role role = roleService.createNewRole(createRoleRequestDto.getRoleName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
