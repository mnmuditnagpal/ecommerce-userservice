package com.userService.UserService.services;

import com.userService.UserService.exceptions.ValueAlreadyExistsException;
import com.userService.UserService.models.Role;
import com.userService.UserService.respositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(String roleName) throws ValueAlreadyExistsException {
        Optional<Role> optionalRole = roleRepository.findByRole(roleName.toLowerCase());

        if(optionalRole.isPresent()){
            throw new ValueAlreadyExistsException("Role Already Exists");
        }

        Role role = new Role();
        role.setRole(roleName);

        return roleRepository.save(role);
    }
}
