package com.someapp.backend.controllers;

import com.someapp.backend.entities.User;
import com.someapp.backend.services.UserDetailsServiceImpl;
import com.someapp.backend.util.customExceptions.BadArgumentException;
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
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/findOwnUserDetailsByUsingGET")
    public User findOwnUserDetails(HttpServletRequest req) {
        return userDetailsService.findOwnUserDetails(req);
    }

    @PostMapping("/findUsersByNameByUsingPOST")
    public List<UserNameIdResponse> findUsersByName(@RequestBody FindUserByNameRequest findUserByNameRequest) {
        return userDetailsService.findUsersByName(findUserByNameRequest);
    }

    @PostMapping("/saveNewUserByUsingPOST")
    public User saveNewUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        try {
            return userDetailsService.save(user);
        } catch (Exception e) {
            throw new BadArgumentException("Given values were not suitable for account.");
        }
    }
}
