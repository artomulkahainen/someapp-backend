package com.someapp.backend.dto;

import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.UUID;

public class RelationshipDTO extends BaseDTO {

    private final UUID userId;
    private final UUID relationshipWithId;
    private final String uniqueId;
    private final int status;

    public RelationshipDTO(final UUID userId, final UUID relationshipWithId,
                           final String uniqueId, final int status,
                           final Date createdDate, final UUID relationshipId) {
        super(relationshipId, createdDate);
        this.userId = userId;
        this.relationshipWithId = relationshipWithId;
        this.uniqueId = uniqueId;
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getRelationshipWithId() {
        return relationshipWithId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public int getStatus() {
        return status;
    }
}
