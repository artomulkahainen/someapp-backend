package com.someapp.backend.utils.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LikePostRequest {

    @NotNull
    private UUID postUserId;
    @NotNull
    private UUID postId;

    public LikePostRequest(UUID postUserId, UUID postId) {
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
