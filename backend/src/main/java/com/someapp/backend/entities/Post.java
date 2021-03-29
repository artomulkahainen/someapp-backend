package com.someapp.backend.entities;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
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

    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments;

    @OneToMany
    @JoinColumn(name = "POST_ID")
    private List<PostLike> postLikes;

    @CreationTimestamp
    private Timestamp timestamp;

    public Post(String post, User user) {
        this.post = post;
        this.user = user;
    }

    public Post() {};

    public List<PostLike> getPostLikes() { return postLikes; }

    public String getPost() {
        return post;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    /*public List<PostLike> getPostLikes() {
        return postLikes;
    }*/

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
