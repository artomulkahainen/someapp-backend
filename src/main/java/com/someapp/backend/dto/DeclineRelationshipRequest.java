package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class DeclineRelationshipRequest {

    @NotNull
    private final String relationshipUniqueId;

    @JsonCreator
    public DeclineRelationshipRequest(
            @JsonProperty("relationshipUniqueId")
            @NotNull
            final String relationshipUniqueId) {
        this.relationshipUniqueId = relationshipUniqueId;
    }

    public String getRelationshipUniqueId() {
        return relationshipUniqueId;
    }
}
