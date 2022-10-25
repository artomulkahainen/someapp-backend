package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LikePostRequest {

    @NotNull
    private final UUID postUserId;
    @NotNull
    private final UUID postId;

    @JsonCreator
    public LikePostRequest(@JsonProperty("postUserId") final UUID postUserId,
                           @JsonProperty("postId") final UUID postId) {
        this.postUserId = postUserId;
        this.postId = postId;
    }

    public UUID getPostUserId() {
        return postUserId;
    }

    public UUID getPostId() {
        return postId;
    }
}
