package com.someapp.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class Post extends AbstractPersistable<UUID> {

    private String post;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments;

    @CreationTimestamp
    private Timestamp timestamp;

    public Post(String post, User user) {
        this.post = post;
        this.user = user;
    }
}
