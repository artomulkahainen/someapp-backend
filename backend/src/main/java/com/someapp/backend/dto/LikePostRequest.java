package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LikePostRequest {

    @NotNull
    private UUID postUserId;
    @NotNull
    private UUID postId;

    @JsonCreator
    public LikePostRequest(@JsonProperty("postUserId") UUID postUserId,
                           @JsonProperty("postId") UUID postId) {
        this.postUserId = postUserId;
        this.postId = postId;
    }

    public UUID getPostUserId() {
        return postUserId;
    }

    public void setUserId(UUID userId) {
        this.postUserId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

}
