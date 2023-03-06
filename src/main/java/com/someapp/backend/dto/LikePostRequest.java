package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LikePostRequest {

    @NotNull
    private final UUID postId;

    @JsonCreator
    public LikePostRequest(@JsonProperty("postId") final UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
