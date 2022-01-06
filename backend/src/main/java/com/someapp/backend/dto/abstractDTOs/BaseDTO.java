package com.someapp.backend.dto.abstractDTOs;

import java.sql.Timestamp;
import java.util.UUID;

public abstract class BaseDTO extends UuidDTO {

    private Timestamp createdDate;

    public BaseDTO(UUID uuid, Timestamp createdDate) {
        super(uuid);
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
