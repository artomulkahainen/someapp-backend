package com.someapp.backend.testUtility.requests;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeletePostRequest {

    @NotNull
    UUID postId;

    public DeletePostRequest(UUID postId) {
        this.postId = postId;
    }

    public DeletePostRequest() {}

    public UUID getPostId() {
        return postId;
    }
}
