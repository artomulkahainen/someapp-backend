package com.someapp.backend.controllers;

import com.someapp.backend.services.UserDetailsServiceImpl;
import com.someapp.backend.util.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.LoginRequest;
import com.someapp.backend.util.responses.JWTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/loginByUsingPOST")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) throws Exception {

        authenticate(user.getUsername(), user.getPassword());

        final ExtendedUserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JWTResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}