package com.someapp.backend.controllers;

import com.someapp.backend.dto.*;
import com.someapp.backend.api.UserApi;
import com.someapp.backend.mappers.UserMapper;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.validators.DeleteUserRequestValidator;
import com.someapp.backend.validators.UserSaveDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    @Autowired
    private ExtendedUserDetailsService extendedUserDetailsService;

    @Autowired
    private UserSaveDTOValidator saveValidator;

    @Autowired
    private DeleteUserRequestValidator deleteValidator;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO findOwnUserDetails() {
        return userMapper.mapUserToUserDTO(extendedUserDetailsService.findOwnUserDetails());
    }

    @Override
    public List<UserNameIdResponse> findUsersByName(final FindUserByNameRequest findUserByNameRequest) {
        return extendedUserDetailsService.findUsersByName(findUserByNameRequest);
    }

    @Override
    public UserDTO saveNewUser(final UserSaveDTO userSaveDTO,
                               final BindingResult bindingResult) throws BindException {
        saveValidator.validate(userSaveDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return userMapper.mapUserToUserDTO(
                extendedUserDetailsService.save(userMapper.mapUserSaveDTOToUser(userSaveDTO)));
    }

    @Override
    @Transactional
    public DeleteResponse deleteUser(final DeleteUserRequest request,
                                     final BindingResult bindingResult) throws BindException {
        deleteValidator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return extendedUserDetailsService.deleteUser(request);
    }
}
