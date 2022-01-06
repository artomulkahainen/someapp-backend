package com.someapp.backend.testUtility.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UnlikePostRequest {

    @NotNull
    private UUID postLikeId;

    public UnlikePostRequest(UUID postLikeId) {
        this.postLikeId = postLikeId;
    }

    public UnlikePostRequest() {}

    public UUID getPostLikeId() {
        return postLikeId;
    }

    public void setPostId(UUID postLikeId) {
        this.postLikeId = postLikeId;
    }
}
