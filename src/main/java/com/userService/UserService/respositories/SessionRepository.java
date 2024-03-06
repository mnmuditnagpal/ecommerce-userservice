package com.userService.UserService.respositories;

import com.userService.UserService.models.Session;
import com.userService.UserService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    List<Session> findAllByUser(User user);

     Optional<Session> findByToken(String token);


}
