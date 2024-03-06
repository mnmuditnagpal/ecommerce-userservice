package com.userService.UserService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name="sessions")
public class Session extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
    private Date expiredDateTime;
    @ManyToOne
    private User user;
    @Column(unique = true)
    private String token;
}
