package com.someapp.backend.util.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class SendPostCommentRequest {

    @Size(min = 1, max = 250, message = "Post comments must be between 1-250 characters.")
    @NotNull
    @NotEmpty
    private String postComment;
    @NotNull
    private UUID postId;
    @NotNull
    private UUID userId;

    public SendPostCommentRequest(String postComment, UUID postId, UUID userId) {
        this.postComment = postComment;
        this.postId = postId;
        this.userId = userId;
    }

    public String getPostComment() {
        return postComment;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getUserId() {
        return userId;
    }


}
