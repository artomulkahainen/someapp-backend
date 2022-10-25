package com.someapp.backend.dto.abstractDTOs;

import java.util.Date;
import java.util.UUID;

public abstract class BaseDTO extends UuidDTO {

    private final Date createdDate;

    public BaseDTO(final UUID uuid, final Date createdDate) {
        super(uuid);
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
