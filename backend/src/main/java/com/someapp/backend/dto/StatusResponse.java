package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StatusResponse(Integer status) {

    @JsonCreator
    public StatusResponse(@JsonProperty("status") Integer status) {
        this.status = status;
    }
}
