package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.sql.Timestamp;
import java.util.UUID;

public class RelationshipDTO extends BaseDTO {

    private UUID friendUuid;
    private UUID actionUserUuid;
    private int status;

    @JsonCreator
    public RelationshipDTO(@JsonProperty("uuid") UUID uuid, @JsonProperty("createdDate") Timestamp createdDate,
                           @JsonProperty("friendUuid") UUID friendUuid,
                           @JsonProperty("actionUserUuid") UUID actionUserUuid, @JsonProperty("status") int status) {
        super(uuid, createdDate);
        this.friendUuid = friendUuid;
        this.actionUserUuid = actionUserUuid;
        this.status = status;
    }

    public UUID getFriendUuid() {
        return friendUuid;
    }

    public void setFriendUuid(UUID friendUuid) {
        this.friendUuid = friendUuid;
    }

    public UUID getActionUserUuid() {
        return actionUserUuid;
    }

    public void setActionUserUuid(UUID actionUserUuid) {
        this.actionUserUuid = actionUserUuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
