package com.someapp.backend.util.requests;

public class FindUserByNameRequest {

    private String username;

    public FindUserByNameRequest(String username) {
        this.username = username;
    }

    public FindUserByNameRequest() {}

    public String getUsername() {
        return username;
    }
}
