package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostDTO extends BaseDTO {

    private final String post;
    private final UUID userUuid;
    private final List<PostCommentDTO> postComments;
    private final List<UUID> postLikerIds;

    @JsonCreator
    public PostDTO(@JsonProperty("uuid") final UUID uuid, @JsonProperty("createdDate") final Date createdDate,
                   @JsonProperty("post") final String post, @JsonProperty("userUuid") final UUID userUuid,
                   @JsonProperty("postComments") final List<PostCommentDTO> postComments,
                   @JsonProperty("postLikerIds") final List<UUID> postLikerIds) {
        super(uuid, createdDate);
        this.post = post;
        this.userUuid = userUuid;
        this.postComments = postComments;
        this.postLikerIds = postLikerIds;
    }

    public String getPost() {
        return post;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public List<PostCommentDTO> getPostComments() {
        return postComments;
    }

    public List<UUID> getPostLikerIds() {
        return postLikerIds;
    }
}
