package com.someapp.backend.dto;

import java.util.UUID;

public class DeleteResponse {

    private final String message;
    private final UUID id;

    public DeleteResponse(final UUID id, final String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
