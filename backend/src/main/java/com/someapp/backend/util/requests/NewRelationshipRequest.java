package com.someapp.backend.util.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class NewRelationshipRequest {

    @NotNull
    private UUID user1Id;
    @NotNull
    private UUID user2Id;
    private UUID actionUserId;
    private int status;

    public NewRelationshipRequest(UUID user1Id, UUID user2Id, UUID actionUserId) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.actionUserId = actionUserId;
        this.status = 0;
    }

    public UUID getUser1Id() {
        return user1Id;
    }

    public UUID getUser2Id() {
        return user2Id;
    }

    public UUID getActionUserId() {
        return actionUserId;
    }

    public int getStatus() {
        return status;
    }

}
