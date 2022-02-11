package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.dto.UserNameIdResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtendedUserDetailsService extends UserDetailsService {

    User findOwnUserDetails();

    List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest);

    User save(User user);

    ExtendedUserDetails loadUserByUsername(String username);

    Optional<User> findUserById(UUID uuid);

    DeleteResponse deleteUser(DeleteUserRequest request);
}
