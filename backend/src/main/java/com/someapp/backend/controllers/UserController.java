package com.someapp.backend.controllers;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws Exception {
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            if (user.getUsername().length() < 3 || user.getPassword().length() < 3) {
                throw new CustomExceptions.BadArgumentsException("Username or password is too short");
            } else {
                throw new CustomExceptions.InternalException("Same username already exists.");
            }
        }
    }
}
