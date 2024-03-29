package com.someapp.backend.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JWTResponse implements Serializable {

    @NotNull
    private final String token;

    private static final long serialVersionUID = -8091879091924046844L;

    public JWTResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}