package com.someapp.backend.dto.abstractDTOs;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public abstract class UuidDTO {

    @NotNull
    private final UUID uuid;

    public UuidDTO(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
