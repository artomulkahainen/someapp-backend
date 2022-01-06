package com.someapp.backend.dto.abstractDTOs;

import java.util.UUID;

public abstract class UuidDTO {

    private UUID uuid;

    public UuidDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
