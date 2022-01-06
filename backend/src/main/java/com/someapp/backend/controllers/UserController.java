package com.someapp.backend.controllers;

import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.interfaces.api.UserApi;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.mappers.UserMapper;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.utils.responses.UserNameIdResponse;
import com.someapp.backend.validators.UserSaveDTOValidator;
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
    private UserSaveDTOValidator validator;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO findOwnUserDetails(HttpServletRequest req) {
        return userMapper.mapUserToUserDTO(extendedUserDetailsService.findOwnUserDetails(req));
    }

    @Override
    public List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest) {
        return extendedUserDetailsService.findUsersByName(findUserByNameRequest);
    }

    @Override
    public UserDTO saveNewUser(UserSaveDTO userSaveDTO, BindingResult bindingResult) throws BindException {
        validator.validate(userSaveDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return userMapper.mapUserToUserDTO(
                extendedUserDetailsService.save(userMapper.mapUserSaveDTOToUser(userSaveDTO)));
    }
}
