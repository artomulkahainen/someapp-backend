package com.someapp.backend.controllers;

import com.someapp.backend.api.LoginApi;
import com.someapp.backend.services.LoginService;
import com.someapp.backend.utils.requests.LoginRequest;
import com.someapp.backend.dto.PingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController implements LoginApi {

    @Autowired
    private LoginService loginService;

    @Override
    public PingResponse pingServerStatus() {
        System.out.println("Server pinged");
        return new PingResponse(HttpStatus.OK, new Date());
    }

    @Override
    public ResponseEntity<?> login(final LoginRequest loginRequest)
            throws Exception {
        return loginService.login(loginRequest);
    }
}
