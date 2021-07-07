package com.someapp.backend.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PostLike extends AbstractPersistable<UUID> {

    @ManyToOne
    Post post;

    @ManyToOne
    User user;

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
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
}