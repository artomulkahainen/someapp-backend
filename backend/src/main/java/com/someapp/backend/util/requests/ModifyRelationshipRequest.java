package com.someapp.backend.util.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ModifyRelationshipRequest {

    @NotNull
    private UUID relationshipId;
    @NotNull
    private UUID actionUserId;
    @NotNull
    @Min(1)
    @Max(3)
    private int status;

    public ModifyRelationshipRequest(UUID relationshipId, UUID actionUserId, int status) {
        this.relationshipId = relationshipId;
        this.actionUserId = actionUserId;
        this.status = status;
    }

    public UUID getRelationshipId() {
        return relationshipId;
    }

    public UUID getActionUserId() {
        return actionUserId;
    }

    public int getStatus() {
        return status;
    }

}
