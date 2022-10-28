package com.someapp.backend.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class PostLike extends AbstractPersistable<UUID> {

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @Column(name = "createdDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public PostLike() {}

    public UUID getPostId() {
        return post.getId();
    }

    public UUID getUserId() {
        return user.getId();
    }

    public UUID getUUID() {
        return this.getId();
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}