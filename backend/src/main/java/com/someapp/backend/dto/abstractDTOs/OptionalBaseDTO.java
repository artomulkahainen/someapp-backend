package com.someapp.backend.dto.abstractDTOs;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class OptionalBaseDTO {

    private Optional<UUID> uuid;
    private Optional<Timestamp> createdDate;

    public OptionalBaseDTO(Optional<UUID> uuid, Optional<Timestamp> createdDate) {
        this.uuid = uuid;
        this.createdDate = createdDate;
    }

    public Optional<UUID> getUuid() {
        return uuid;
    }

    public void setUuid(Optional<UUID> uuid) {
        this.uuid = uuid;
    }

    public Optional<Timestamp> getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Optional<Timestamp> createdDate) {
        this.createdDate = createdDate;
    }
}
