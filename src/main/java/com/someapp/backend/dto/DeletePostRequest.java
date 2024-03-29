package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.UuidDTO;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeletePostRequest extends UuidDTO {

    @JsonCreator
    public DeletePostRequest(@JsonProperty("uuid") final UUID uuid) {
        super(uuid);
    }
}
