package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class StatusResponse {

    @NotNull
    private final Integer status;

    @JsonCreator
    public StatusResponse(@JsonProperty("status") final Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
