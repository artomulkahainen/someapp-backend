package com.someapp.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeleteResponse {

    private final String message;
    @NotNull
    private final UUID id;
    @NotNull
    private final UUID actionUserId;

    public DeleteResponse(final UUID id, final UUID actionUserId, final String message) {
        this.id = id;
        this.actionUserId = actionUserId;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public UUID getActionUserId() {
        return actionUserId;
    }

    public String getMessage() {
        return message;
    }
}
