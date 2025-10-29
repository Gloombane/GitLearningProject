package com.example.gitlearningprojectwithspringandreact.services.userService;

import com.example.gitlearningprojectwithspringandreact.entities.User;

import java.util.Optional;

public interface UserService {
    boolean existsByEmail(String email);
    void registerUser(User user);
    Optional<User> findByEmail(String email);
}
