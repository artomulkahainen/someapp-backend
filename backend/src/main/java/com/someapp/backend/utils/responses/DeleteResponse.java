package com.someapp.backend.utils.responses;

import java.util.UUID;

public class DeleteResponse {

    public String message;
    public UUID id;
    public int status;

    public DeleteResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public DeleteResponse(UUID id, String message, int status) {
        this.id = id;
        this.message = message;
        this.status = status;
    }

    public DeleteResponse(UUID id, String message) {
        this.message = message;
        this.id = id;
    }

    public DeleteResponse() {}

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
