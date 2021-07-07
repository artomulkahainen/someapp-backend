package com.someapp.backend.controllers;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.requests.FindUserByNameRequest;
import com.someapp.backend.util.responses.UserNameIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @GetMapping("/findOwnUserDetailsByUsingGET")
    public User findOwnUserDetails(HttpServletRequest req) {
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(req.getHeader("Authorization").substring(7));
        return userRepository.findByUsername(usernameFromToken);
    }

    @PostMapping("/findUsersByNameByUsingPOST")
    public List<UserNameIdResponse> findUsersByName(@RequestBody FindUserByNameRequest findUserByNameRequest) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().contains(findUserByNameRequest.getUsername()))
                .map(user -> new UserNameIdResponse(user.getUsername(), user.getId()))
                .collect(Collectors.toList());
    }

    @PostMapping("/saveNewUserByUsingPOST")
    public User saveNewUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new BadArgumentException("Given values were not suitable for account.");
        }
    }
}
