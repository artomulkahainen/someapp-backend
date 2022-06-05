package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SaveRelationshipDTO {

    @NotNull
    private UUID relationshipWithId;
    @NotNull
    private String uniqueId;
    @NotNull
    @Min(0)
    @Max(2)
    private int status;

    @JsonCreator
    public SaveRelationshipDTO(@JsonProperty("relationshipWithId") UUID relationshipWithId,
                               @JsonProperty("uniqueId") String uniqueId,
                               @JsonProperty("status") int status) {
        this.relationshipWithId = relationshipWithId;
        this.uniqueId = uniqueId;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UUID getRelationshipWithId() {
        return relationshipWithId;
    }

    public void setRelationshipWithId(UUID relationshipWithId) {
        this.relationshipWithId = relationshipWithId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getActionUserId() {
        return UUID.fromString(uniqueId.split(",")[0]);
    }

    public UUID getNonActionUserId() {
        return UUID.fromString(uniqueId.split(",")[1]);
    }
}
