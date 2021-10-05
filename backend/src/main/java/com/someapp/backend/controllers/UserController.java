package com.someapp.backend.controllers;

import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.UserDetailsServiceImpl;
import com.someapp.backend.util.mappers.UserMapper;
import com.someapp.backend.util.requests.FindUserByNameRequest;
import com.someapp.backend.util.responses.UserNameIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private UserMapper userMapper;

    @GetMapping("/findOwnUserDetailsByUsingGET")
    public UserDTO findOwnUserDetails(HttpServletRequest req) {
        User user = userDetailsService.findOwnUserDetails(req);
        return userMapper.mapUserToUserDTO(user);
    }

    @PostMapping("/findUsersByNameByUsingPOST")
    public List<UserNameIdResponse> findUsersByName(@RequestBody FindUserByNameRequest findUserByNameRequest) {
        return userDetailsService.findUsersByName(findUserByNameRequest);
    }

    @PostMapping("/saveNewUserByUsingPOST")
    public UserDTO saveNewUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User savedUser = userDetailsService.save(user);
        return userMapper.mapUserToUserDTO(savedUser);
    }
}
