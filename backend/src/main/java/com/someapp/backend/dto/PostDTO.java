package com.someapp.backend.dto;

import com.someapp.backend.entities.PostComment;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class PostDTO extends BaseDTO {

    private String post;
    private UUID userUuid;
    private List<PostComment> postComments;
    private List<UUID> postLikerIds;

    public PostDTO(UUID uuid, Timestamp createdDate,
                   String post, UUID userUuid,
                   List<PostComment> postComments, List<UUID> postLikerIds) {
        super(uuid, createdDate);
        this.post = post;
        this.userUuid = userUuid;
        this.postComments = postComments;
        this.postLikerIds = postLikerIds;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public List<UUID> getPostLikerIds() {
        return postLikerIds;
    }

    public void setPostLikerIds(List<UUID> postLikerIds) {
        this.postLikerIds = postLikerIds;
    }
}
