package com.someapp.backend.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class BaseDTO {

    private UUID uuid;
    private Timestamp createdDate;

    public BaseDTO(UUID uuid, Timestamp createdDate) {
        this.uuid = uuid;
        this.createdDate = createdDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
