package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SendPostRequest {

    @Size(min = 1, max = 250, message = "Post length must be between 1-250 letters.")
    @NotNull
    @NotEmpty
    private final String post;

    @JsonCreator
    public SendPostRequest(@JsonProperty("post") final String post) {
        this.post = post;
    }

    public String getPost() {
        return post;
    }
}
