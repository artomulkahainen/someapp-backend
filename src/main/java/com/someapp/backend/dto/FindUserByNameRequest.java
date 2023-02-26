package com.someapp.backend.dto;

import javax.validation.constraints.NotNull;

public class FindUserByNameRequest {

    @NotNull
    private String username;

    public FindUserByNameRequest(String username) {
        this.username = username;
    }

    public FindUserByNameRequest() {}

    public String getUsername() {
        return username;
    }
}
