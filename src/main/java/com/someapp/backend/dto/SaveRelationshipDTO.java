package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SaveRelationshipDTO {

    @NotNull
    private final UUID relationshipWithId;
    @NotNull
    private final String uniqueId;
    @NotNull
    @Min(0)
    @Max(2)
    private final int status;

    @JsonCreator
    public SaveRelationshipDTO(@JsonProperty("relationshipWithId") final UUID relationshipWithId,
                               @JsonProperty("uniqueId") final String uniqueId,
                               @JsonProperty("status") final int status) {
        this.relationshipWithId = relationshipWithId;
        this.uniqueId = uniqueId;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public UUID getRelationshipWithId() {
        return relationshipWithId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public UUID getActionUserId() {
        return UUID.fromString(uniqueId.split(",")[0]);
    }

    public UUID getNonActionUserId() {
        return UUID.fromString(uniqueId.split(",")[1]);
    }
}
