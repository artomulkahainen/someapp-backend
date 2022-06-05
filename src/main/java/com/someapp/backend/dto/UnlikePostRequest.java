package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.UuidDTO;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UnlikePostRequest extends UuidDTO {

    @JsonCreator
    public UnlikePostRequest(@JsonProperty("uuid") UUID uuid) {
        super(uuid);
    }
}
