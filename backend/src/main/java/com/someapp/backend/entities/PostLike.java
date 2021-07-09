package com.someapp.backend.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PostLike extends AbstractPersistable<UUID> {

    @ManyToOne
    Post post;

    UUID postUUID;

    @ManyToOne
    User user;

    UUID userUUID;

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
        this.postUUID = post.getUUID();
        this.userUUID = user.getUUID();
    }

    public PostLike() {};

    public UUID getPostId() {
        return post.getId();
    }

    public UUID getUserId() {
        return user.getId();
    }

    public UUID getUUID() {
        return this.getId();
    }

    public UUID getPostUUID() {
        return postUUID;
    }

    public void setPostUUID(UUID postUUID) {
        this.postUUID = postUUID;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }
}