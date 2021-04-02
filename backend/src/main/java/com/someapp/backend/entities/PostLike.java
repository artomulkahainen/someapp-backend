package com.someapp.backend.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class PostLike extends AbstractPersistable<UUID> {

    @ManyToOne (cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @NotNull
    @JoinColumn(name="POST_ID")
    Post post;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @NotNull
    @JoinColumn(name="USER_ID")
    User user;

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public PostLike() {};

    public UUID getPostId() {
        return this.post.getId();
    }

    public UUID getUserId() {
        return this.user.getId();
    }
}