package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class PostCommentSaveDTO {

    @Size(min = 1, max = 250, message = "Post comments must be between 1-250 characters.")
    @NotNull
    @NotEmpty
    private String postComment;
    @NotNull
    private UUID postId;

    @JsonCreator
    public PostCommentSaveDTO(@JsonProperty("postComment") String postComment,
                              @JsonProperty("postId") UUID postId) {
        this.postComment = postComment;
        this.postId = postId;
    }

    public String getPostComment() {
        return postComment;
    }

    public UUID getPostId() {
        return postId;
    }
}
