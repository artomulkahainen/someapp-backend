package com.someapp.backend.util.requests;

import java.util.UUID;

public class UUIDRequest {

    private UUID uuid;

    public UUIDRequest(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}
