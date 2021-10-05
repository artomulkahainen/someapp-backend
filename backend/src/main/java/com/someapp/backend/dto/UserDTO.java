package com.someapp.backend.dto;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class UserDTO extends BaseDTO {

    private String username;
    private boolean admin;
    private List<Post> posts;
    private List<UUID> likedPostsIds;

    public UserDTO(UUID uuid, Timestamp createdDate,
                   String username, boolean admin,
                   List<Post> posts, List<UUID> likedPostsIds) {
        super(uuid, createdDate);
        this.username = username;
        this.admin = admin;
        this.posts = posts;
        this.likedPostsIds = likedPostsIds;
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

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<UUID> getLikedPostsIds() {
        return likedPostsIds;
    }

    public void setLikedPostsIds(List<UUID> likedPostsIds) {
        this.likedPostsIds = likedPostsIds;
    }
}
