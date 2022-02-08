package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.utils.requests.UUIDRequest;

import java.util.UUID;

public class DeletePostRequest extends UUIDRequest {

    @JsonCreator
    public DeletePostRequest(@JsonProperty("uuid") UUID uuid) {
        super(uuid);
    }
}
