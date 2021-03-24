package com.someapp.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class User extends AbstractPersistable<UUID> {

    private String username;
    private String password;

    @CreationTimestamp
    private Timestamp timestamp;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<PostComment> postComments;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {};

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }
}
