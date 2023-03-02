package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class PostCommentDTO extends BaseDTO {

    @NotNull
    private final String postComment;
    @NotNull
    private final UUID postId;
    @NotNull
    private final UUID userId;
    @NotNull
    private final String username;

    @JsonCreator
    public PostCommentDTO(@JsonProperty("uuid") final UUID uuid,
                          @JsonProperty("createdDate") final Date date,
                          @JsonProperty("postComment") final String postComment,
                          @JsonProperty("postId") final UUID postId,
                          @JsonProperty("userId") final UUID userId,
                          @JsonProperty("username") final String username) {
        super(uuid, date);
        this.postComment = postComment;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
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

    public String getUsername() {
        return this.username;
    }
}
