package com.someapp.backend.util.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RelationshipRequest {

    @NotNull
    private UUID user1Id;
    @NotNull
    private UUID user2Id;
    private UUID actionUserId;
    @NotNull
    private int status;

    public RelationshipRequest(UUID user1, UUID user2, UUID action_user_id, int status) {
        this.user1Id = user1;
        this.user2Id = user2;
        this.actionUserId = action_user_id;
        this.status = status;
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
