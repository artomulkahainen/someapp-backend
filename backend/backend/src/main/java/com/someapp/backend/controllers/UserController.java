package com.someapp.backend.controllers;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    @ResponseBody
    public String create(@RequestBody User user) {
        if (userRepository
                .findAll()
                .stream()
                .map(repoUser -> repoUser.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList())
                .isEmpty()) {
            userRepository.save(user);
            return "User " + user.getUsername() + " saved successfully!";
        } else {
            return "Given username is already in use.";
        }
    }
}
