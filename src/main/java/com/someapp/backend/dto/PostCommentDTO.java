package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.UUID;

public class PostCommentDTO extends BaseDTO {

    private String postComment;
    private UUID postId;
    private UUID userId;

    @JsonCreator
    public PostCommentDTO(@JsonProperty("uuid") UUID uuid,
                          @JsonProperty("createdDate") Date date,
                          @JsonProperty("postComment") String postComment,
                          @JsonProperty("postId") UUID postId,
                          @JsonProperty("userId") UUID userId) {
        super(uuid, date);
        this.postComment = postComment;
        this.postId = postId;
        this.userId = userId;
    }

    public String getPostComment() {
        return postComment;
    }

    public void setPostComment(String postComment) {
        this.postComment = postComment;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
