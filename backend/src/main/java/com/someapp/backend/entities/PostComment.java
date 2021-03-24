package com.someapp.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class PostComment extends AbstractPersistable<UUID> {

    private String postComment;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private Timestamp timestamp;

}
