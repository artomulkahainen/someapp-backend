package com.someapp.backend.api;

import com.someapp.backend.dto.LoginRequest;
import com.someapp.backend.dto.PingResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginApi {

    @GetMapping("/ping")
    PingResponse pingServerStatus();

    @PostMapping("/loginByUsingPOST")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
            throws Exception;

}
