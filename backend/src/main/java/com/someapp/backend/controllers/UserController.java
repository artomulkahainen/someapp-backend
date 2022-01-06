package com.someapp.backend.controllers;

import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.api.UserApi;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.mappers.UserMapper;
import com.someapp.backend.testUtility.requests.FindUserByNameRequest;
import com.someapp.backend.testUtility.responses.UserNameIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController implements UserApi {

    @Autowired
    private ExtendedUserDetailsService extendedUserDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO findOwnUserDetails(HttpServletRequest req) {
        User user = extendedUserDetailsService.findOwnUserDetails(req);
        return userMapper.mapUserToUserDTO(user);
    }

    @Override
    public List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest) {
        return extendedUserDetailsService.findUsersByName(findUserByNameRequest);
    }

    @Override
    public UserDTO saveNewUser(User user, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User savedUser = extendedUserDetailsService.save(user);
        return userMapper.mapUserToUserDTO(savedUser);
    }
}
