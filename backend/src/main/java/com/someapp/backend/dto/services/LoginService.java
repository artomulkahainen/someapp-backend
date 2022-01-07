package com.someapp.backend.dto.services;

import com.someapp.backend.utils.requests.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest loginRequest) throws Exception;
}
