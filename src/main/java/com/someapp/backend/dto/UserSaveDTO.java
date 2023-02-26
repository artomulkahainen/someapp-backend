package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class UserSaveDTO {

    @NotNull
    private final String username;
    @NotNull
    private final String password;

    @JsonCreator
    public UserSaveDTO(@JsonProperty("username") final String username,
                       @JsonProperty("password") final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
