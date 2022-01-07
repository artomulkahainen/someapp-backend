package com.someapp.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.services.ExtendedUserDetailsService;
import com.someapp.backend.dto.services.LoginServiceImpl;
import com.someapp.backend.entities.User;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.LoginRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JWTTokenUtil jwtTokenUtil;
    @Mock
    UserRepository userRepository;
    @Mock
    ExtendedUser extendedUser;
    @Mock
    User user;
    @Mock
    ExtendedUserDetails userDetails;
    @Mock
    ExtendedUserDetailsService extendedUserDetailsService;
    @InjectMocks
    LoginServiceImpl loginService;

    @Test
    public void loginIsSuccessful() throws Exception {
        user = new User("donald", "duck");
        extendedUser = new ExtendedUser(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"),
                "donald", "duck", ImmutableList.of());
        when(userRepository.findByUsername("donald")).thenReturn(Optional.of(user));
        when(extendedUserDetailsService.loadUserByUsername("donald")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("donald");
        when(jwtTokenUtil.generateToken(any()))
                .thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                        ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                        ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        ResponseEntity response = loginService.login(new LoginRequest("donald", "duck"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
