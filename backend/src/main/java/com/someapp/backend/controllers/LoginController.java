package com.someapp.backend.controllers;

import com.someapp.backend.services.LoginServiceImpl;
import com.someapp.backend.util.requests.LoginRequest;
import com.someapp.backend.util.responses.PingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @GetMapping("/ping")
    public PingResponse pingServerStatus() {
        System.out.println("Server pinged");
        return new PingResponse(HttpStatus.OK, new Date());
    }

    @PostMapping("/loginByUsingPOST")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return loginService.login(loginRequest);
    }
}
