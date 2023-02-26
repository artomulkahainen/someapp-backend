package com.someapp.backend.services;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.entities.User;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.utils.ExtendedUserDetails;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.dto.LoginRequest;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private ExtendedUser extendedUser;
    @Mock
    private User user;
    @Mock
    private ExtendedUserDetails userDetails;
    @Mock
    private ExtendedUserDetailsService extendedUserDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void loginIsSuccessful() throws Exception {
        user = new User("donald", "duck");
        extendedUser = new ExtendedUser(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"),
                "donald", "duck", ImmutableList.of());
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
