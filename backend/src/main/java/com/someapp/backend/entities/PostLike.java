package com.someapp.backend.entities;

import com.someapp.backend.util.embeddedkeys.PostLikeKey;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(PostLikeKey.class)
public class PostLike {

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private UUID userId;

    @Id
    @Column(name = "post_id", insertable = false, updatable = false)
    private UUID postId;

    @ManyToOne
    @NotNull
    private Post post;

    @ManyToOne
    @NotNull
    private User user;

    @CreationTimestamp
    private Timestamp timestamp;
}