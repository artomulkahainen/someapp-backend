package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DeclineRelationshipRequest(String relationshipUniqueId) {

    @JsonCreator
    public DeclineRelationshipRequest(@JsonProperty("relationshipUniqueId") String relationshipUniqueId) {
        this.relationshipUniqueId = relationshipUniqueId;
    }
}
