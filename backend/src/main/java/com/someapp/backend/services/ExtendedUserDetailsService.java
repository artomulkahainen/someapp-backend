package com.someapp.backend.services;

import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.util.requests.FindUserByNameRequest;
import com.someapp.backend.util.responses.UserNameIdResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExtendedUserDetailsService extends UserDetailsService {

    User findOwnUserDetails(HttpServletRequest req);

    List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest);

    User save(User user);

    ExtendedUserDetails loadUserByUsername(String username);
}
