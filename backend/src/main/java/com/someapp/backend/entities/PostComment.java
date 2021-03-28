package com.someapp.backend.entities;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class PostComment extends AbstractPersistable<UUID> {

    @NotNull
    @Size(min = 1, max = 250, message = "Post comment length must be between 1-250 characters.")
    private String postComment;

    @ManyToOne
    private Post post;

    @ManyToOne(cascade = { CascadeType.ALL })
    private User user;

    @CreationTimestamp
    private Timestamp timestamp;

    public PostComment(String postComment, Post post, User user) {
        this.postComment = postComment;
        this.post = post;
        this.user = user;
    }

    public PostComment() {};

    public String getPostComment() { return this.postComment; }

    public UUID getPostId() {
        return this.post.getId();
    }

    public UUID getUserId() {
        return this.user.getId();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
