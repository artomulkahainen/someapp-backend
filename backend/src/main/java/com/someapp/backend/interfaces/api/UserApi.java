package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.entities.User;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.utils.responses.UserNameIdResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserApi {

    @GetMapping("/findOwnUserDetailsByUsingGET")
    UserDTO findOwnUserDetails(@NotNull HttpServletRequest req);

    @PostMapping("/findUsersByNameByUsingPOST")
    List<UserNameIdResponse> findUsersByName(@RequestBody FindUserByNameRequest findUserByNameRequest);

    @PostMapping("/saveNewUserByUsingPOST")
    UserDTO saveNewUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindException;

}
