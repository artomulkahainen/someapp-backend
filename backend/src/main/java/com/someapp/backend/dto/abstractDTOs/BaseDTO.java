package com.someapp.backend.dto.abstractDTOs;

import java.util.Date;
import java.util.UUID;

public abstract class BaseDTO extends UuidDTO {

    private Date createdDate;

    public BaseDTO(UUID uuid, Date createdDate) {
        super(uuid);
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
