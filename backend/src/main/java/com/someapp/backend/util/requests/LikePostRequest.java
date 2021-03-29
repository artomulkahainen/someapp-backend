package com.someapp.backend.util.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LikePostRequest {

    @NotNull
    private UUID userId;
    @NotNull
    private UUID postId;

    public LikePostRequest(UUID userId, UUID postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

}
