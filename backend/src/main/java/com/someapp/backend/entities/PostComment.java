package com.someapp.backend.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
public class PostComment extends AbstractPersistable<UUID> {

    @NotNull
    @Size(min = 1, max = 250, message = "Post comment length must be between 1-250 characters.")
    private String postComment;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @Column(name = "createdDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public UUID getUUID() {
        return this.getId();
    }

    public void setUUID(UUID uuid) {
        this.setId(uuid);
    }
}
