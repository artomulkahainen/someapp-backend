package com.someapp.backend.entities;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Post extends AbstractPersistable<UUID> {

    @NotNull
    @Size(min = 1, max = 250)
    private String post;

    @ManyToOne
    @NotNull
    private User user;

    @OneToMany(mappedBy = "post", cascade = { CascadeType.REMOVE })
    private List<PostComment> postComments;

    @OneToMany(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "POST_ID")
    private List<PostLike> postLikes;

    @Column(name = "createdDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    public Post(String post, User user) {
        this.post = post;
        this.user = user;
        this.postComments = new ArrayList<>();
        this.postLikes = new ArrayList<>();
    }

    public Post() {}

    public List<PostLike> getPostLikes() { return postLikes; }

    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getUUID() {
        return this.getId();
    }

    public void setUUID(UUID uuid) {
        this.setId(uuid);
    }
}
