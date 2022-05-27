package com.someapp.backend.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends AbstractPersistable<UUID> {

    @Size(min = 3, max = 15, message = "Username length must be between 3-15 letters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(min = 3, message = "Password must be longer or equal to 3")
    private String password;

    private boolean admin;

    @Column(name = "createdDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<Relationship> relationships;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.admin = false;
        this.posts = new ArrayList<>();
        this.postLikes = new ArrayList<>();
    }

    public User() {};

    public UUID getUUID() {
        return this.getId();
    }

    public void setUUID(UUID uuid) {
        this.setId(uuid);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return this.password; };

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PostLike> getPostLikes() {
        return postLikes;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean status) {
        this.admin = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public List<Post> getPosts() {
        Comparator<Post> byCreatedDate = Comparator.comparing(Post::getCreatedDate).reversed();
        return posts.stream().sorted(byCreatedDate).limit(10).collect(Collectors.toList());
    }
}
