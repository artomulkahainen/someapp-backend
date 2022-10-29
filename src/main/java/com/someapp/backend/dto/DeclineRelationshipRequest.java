package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeclineRelationshipRequest {

    private final String relationshipUniqueId;

    @JsonCreator
    public DeclineRelationshipRequest(
            @JsonProperty("relationshipUniqueId")
            final String relationshipUniqueId) {
        this.relationshipUniqueId = relationshipUniqueId;
    }

    public String getRelationshipUniqueId() {
        return relationshipUniqueId;
    }
}
