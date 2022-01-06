package com.someapp.backend.interfaces.api;

import com.someapp.backend.utils.requests.LoginRequest;
import com.someapp.backend.utils.responses.PingResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginApi {

    @GetMapping("/ping")
    PingResponse pingServerStatus();

    @PostMapping("/loginByUsingPOST")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;

}
