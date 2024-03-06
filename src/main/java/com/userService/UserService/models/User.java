package com.userService.UserService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="users")
public class User extends BaseModel{
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();
}
