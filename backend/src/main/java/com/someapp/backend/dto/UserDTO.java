package com.someapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDTO extends BaseDTO {

    private final String username;
    private final boolean admin;
    private final List<PostDTO> posts;
    private final List<PostLikeDTO> likedPosts;
    private final List<RelationshipDTO> relationships;

    @JsonCreator
    public UserDTO(@JsonProperty("uuid") final UUID uuid, @JsonProperty("createdDate") final Date createdDate,
                   @JsonProperty("username") final String username, @JsonProperty("admin") final boolean admin,
                   @JsonProperty("posts") final List<PostDTO> posts,
                   @JsonProperty("likedPostsIds") final List<PostLikeDTO> likedPosts,
                   @JsonProperty("relationships") final List<RelationshipDTO> relationships) {
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
