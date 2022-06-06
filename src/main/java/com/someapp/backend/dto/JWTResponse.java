package com.someapp.backend.dto;

import java.io.Serializable;

public class JWTResponse implements Serializable {

    private String token;

    private static final long serialVersionUID = -8091879091924046844L;

    public JWTResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}