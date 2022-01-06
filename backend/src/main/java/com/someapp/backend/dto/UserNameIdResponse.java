package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.UuidDTO;
import java.util.UUID;

public class UserNameIdResponse extends UuidDTO {

    private String username;

    @JsonCreator
    public UserNameIdResponse(@JsonProperty("userUuid") UUID userUuid, @JsonProperty("username") String username) {
        super(userUuid);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
