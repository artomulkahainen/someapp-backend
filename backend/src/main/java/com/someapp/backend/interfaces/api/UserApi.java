package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.*;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface UserApi {

    @GetMapping("/findOwnUserDetailsByUsingGET")
    UserDTO findOwnUserDetails();

    @PostMapping("/findUsersByNameByUsingPOST")
    List<UserNameIdResponse> findUsersByName(@RequestBody FindUserByNameRequest findUserByNameRequest);

    @PostMapping("/saveNewUserByUsingPOST")
    UserDTO saveNewUser(@Valid @RequestBody UserSaveDTO userSaveDTO, BindingResult bindingResult) throws BindException;

    @PostMapping("/deleteUserByUsingPOST")
    DeleteResponse deleteUser(@Valid @RequestBody DeleteUserRequest request, BindingResult bindingResult) throws BindException;
}
