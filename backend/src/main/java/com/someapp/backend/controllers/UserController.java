package com.someapp.backend.controllers;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
    public User create(@Valid @RequestBody User user) {
            userRepository.save(user);
            return user;
    }
}
