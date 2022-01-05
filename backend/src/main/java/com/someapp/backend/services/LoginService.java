package com.someapp.backend.services;

import com.someapp.backend.util.requests.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest loginRequest) throws Exception;
}
