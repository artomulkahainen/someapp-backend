package com.someapp.backend.util.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UUIDRequest {

    @NotNull
    private UUID uuid;

    public UUIDRequest(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}
