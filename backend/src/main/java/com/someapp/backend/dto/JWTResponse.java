package com.someapp.backend.dto;

import java.io.Serializable;

public record JWTResponse(String token) implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
}