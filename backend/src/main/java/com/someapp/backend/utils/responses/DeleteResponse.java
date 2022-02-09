package com.someapp.backend.utils.responses;

import java.util.UUID;

public class DeleteResponse {

    public String message;
    public UUID id;

    public DeleteResponse(String message) {
        this.message = message;
    }

    public DeleteResponse(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
