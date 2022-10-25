package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.UuidDTO;

import java.util.UUID;

public class DeleteUserRequest extends UuidDTO {

    @JsonCreator
    public DeleteUserRequest(@JsonProperty("uuid") final UUID uuid) {
        super(uuid);
    }
}
