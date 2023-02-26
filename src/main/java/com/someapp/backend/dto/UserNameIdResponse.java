package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.UuidDTO;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UserNameIdResponse extends UuidDTO {

    @NotNull
    private final String username;

    @JsonCreator
    public UserNameIdResponse(@JsonProperty("userUuid") final UUID userUuid,
                              @JsonProperty("username") final String username) {
        super(userUuid);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
