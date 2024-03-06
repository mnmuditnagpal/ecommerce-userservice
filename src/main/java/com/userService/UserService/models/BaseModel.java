package com.userService.UserService.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(generator = "uuid2Generator")
    @GenericGenerator(name="uuid2Generator",strategy = "uuid2")
    @Column(name="id",unique = true,nullable = false,updatable = false)
    private UUID id;
}
