package com.someapp.backend.utils.responses;

import java.util.UUID;

public class UserNameIdResponse {

    private UUID id;

    private String username;

    public UserNameIdResponse(String username, UUID id) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
