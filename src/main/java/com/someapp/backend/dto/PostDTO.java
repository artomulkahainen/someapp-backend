package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostDTO extends BaseDTO {

    @NotNull
    private final String post;
    @NotNull
    private final UUID userUuid;
    @NotNull
    private final String username;
    private final List<PostCommentDTO> postComments;
    private final List<UUID> postLikerIds;

    @JsonCreator
    public PostDTO(@JsonProperty("uuid") final UUID uuid, @JsonProperty("createdDate") final Date createdDate,
                   @JsonProperty("post") final String post, @JsonProperty("userUuid") final UUID userUuid,
                   @JsonProperty("username") final String username,
                   @JsonProperty("postComments") final List<PostCommentDTO> postComments,
                   @JsonProperty("postLikerIds") final List<UUID> postLikerIds) {
        super(uuid, createdDate);
        this.post = post;
        this.userUuid = userUuid;
        this.username = username;
        this.postComments = postComments;
        this.postLikerIds = postLikerIds;
    }

    public String getPost() {
        return post;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public String getUsername() {
        return this.username;
    }
}
