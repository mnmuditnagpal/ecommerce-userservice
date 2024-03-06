package com.userService.UserService.services;

import com.userService.UserService.dtos.UserDto;
import com.userService.UserService.exceptions.NotFoundException;
import com.userService.UserService.models.Role;
import com.userService.UserService.models.User;
import com.userService.UserService.respositories.RoleRepository;
import com.userService.UserService.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(String id) throws NotFoundException{
        UUID uuid = UUID.fromString(id);

        Optional<User> userOptional = userRepository.findById(uuid);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User Id Not Found");
        }

        User user = userOptional.get();
        return UserDto.from(user);
    }

    public UserDto setUserRoles(String id, List<String> roleIds) throws NotFoundException {
        UUID userUUID = UUID.fromString(id);

        Optional<User> userOptional = userRepository.findById(userUUID);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User Id Not Found");
        }

        Set<Role> roleSet = new HashSet<>();
        for(String roleId : roleIds){
            Optional<Role> optionalRole = roleRepository.findById(UUID.fromString(roleId));
            if(optionalRole.isEmpty()){
                throw new NotFoundException("Role ID Not Found");
            }
            roleSet.add(optionalRole.get());
        }

        User user = userOptional.get();
        user.setRoles(roleSet);

        return UserDto.from(userRepository.save(user));
    }
}
