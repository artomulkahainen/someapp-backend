package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;
import com.someapp.backend.dto.abstractDTOs.OptionalBaseDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class RelationshipDTO extends OptionalBaseDTO {

    @NotNull
    private UUID relationshipWithId;
    @NotNull
    private String uniqueId;
    @NotNull
    @Min(0)
    @Max(3)
    private int status;

    @JsonCreator
    public RelationshipDTO(@JsonProperty("relationshipWithId") UUID relationshipWithId,
                           @JsonProperty("uniqueId") String uniqueId,
                           @JsonProperty("status") int status,
                           @JsonProperty("relationshipId") Optional<UUID> relationshipId,
                           @JsonProperty("createdDate") Optional<Timestamp> createdDate) {
        super(relationshipId, createdDate);
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
}
