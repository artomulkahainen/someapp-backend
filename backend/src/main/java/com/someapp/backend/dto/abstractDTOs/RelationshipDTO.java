package com.someapp.backend.dto.abstractDTOs;

import java.sql.Timestamp;
import java.util.UUID;

public class RelationshipDTO extends BaseDTO {

    private UUID userId;
    private UUID relationshipWithId;
    private String uniqueId;
    private int status;

    public RelationshipDTO(UUID userId, UUID relationshipWithId,
                           String uniqueId, int status, Timestamp createdDate, UUID relationshipId) {
        super(relationshipId, createdDate);
        this.userId = userId;
        this.relationshipWithId = relationshipWithId;
        this.uniqueId = uniqueId;
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
