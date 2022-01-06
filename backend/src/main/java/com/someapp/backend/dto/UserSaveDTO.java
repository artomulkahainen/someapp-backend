package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSaveDTO {

    @Size(min = 3, max = 15, message = "Username length must be between 3-15 letters")
    @NotNull
    private String username;
    @NotNull
    @Size(min = 3, message = "Password must be longer or equal to 3")
    private String password;

    @JsonCreator
    public UserSaveDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
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
