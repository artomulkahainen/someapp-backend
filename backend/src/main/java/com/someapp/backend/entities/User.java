package com.someapp.backend.entities;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class User extends AbstractPersistable<UUID> {

    @Size(min = 3, message = "Username must be bigger or equal to 3")
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(min = 3, message = "Password must be longer or equal to 3")
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