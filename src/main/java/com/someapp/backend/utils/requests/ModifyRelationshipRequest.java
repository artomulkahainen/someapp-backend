package com.someapp.backend.utils.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ModifyRelationshipRequest {

    @NotNull
    private UUID relationshipId;
    @NotNull
    @Min(1)
    @Max(3)
    private int status;

    public ModifyRelationshipRequest(UUID relationshipId, int status) {
        this.relationshipId = relationshipId;
        this.status = status;
    }

    public UUID getRelationshipId() {
        return relationshipId;
    }

    public int getStatus() {
        return status;
    }

}
