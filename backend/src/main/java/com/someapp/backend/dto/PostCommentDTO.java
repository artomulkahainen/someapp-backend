package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.UUID;

public class PostCommentDTO extends BaseDTO {

    private final String postComment;
    private final UUID postId;
    private final UUID userId;

    @JsonCreator
    public PostCommentDTO(@JsonProperty("uuid") final UUID uuid,
                          @JsonProperty("createdDate") final Date date,
                          @JsonProperty("postComment") final String postComment,
                          @JsonProperty("postId") final UUID postId,
                          @JsonProperty("userId") final UUID userId) {
        super(uuid, date);
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
