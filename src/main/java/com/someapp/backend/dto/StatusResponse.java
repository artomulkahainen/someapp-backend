package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusResponse {

    private Integer status;

    @JsonCreator
    public StatusResponse(@JsonProperty("status") Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
