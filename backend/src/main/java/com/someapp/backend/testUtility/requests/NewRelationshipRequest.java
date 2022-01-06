package com.someapp.backend.testUtility.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class NewRelationshipRequest {

    @NotNull
    private UUID addedUserId;

    private int status;

    public NewRelationshipRequest(UUID addedUserId) {
        this.addedUserId = addedUserId;
        this.status = 0;
    }

    public NewRelationshipRequest() {}

    public UUID getAddedUserId() {
        return addedUserId;
    }

    public int getStatus() {
        return status;
    }

}
