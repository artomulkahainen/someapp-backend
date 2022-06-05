package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDTO extends BaseDTO {

    private String username;
    private boolean admin;
    private List<PostDTO> posts;
    private List<PostLikeDTO> likedPosts;
    private List<RelationshipDTO> relationships;

    @JsonCreator
    public UserDTO(@JsonProperty("uuid") UUID uuid, @JsonProperty("createdDate") Date createdDate,
                   @JsonProperty("username") String username, @JsonProperty("admin") boolean admin,
                   @JsonProperty("posts") List<PostDTO> posts, @JsonProperty("likedPostsIds") List<PostLikeDTO> likedPosts,
                   @JsonProperty("relationships") List<RelationshipDTO> relationships) {
        super(uuid, createdDate);
        this.username = username;
        this.admin = admin;
        this.posts = posts;
        this.likedPosts = likedPosts;
        this.relationships = relationships;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public List<PostLikeDTO> getLikedPosts() {
        return likedPosts;
    }

    public List<RelationshipDTO> getRelationships() {
        return relationships;
    }
}
