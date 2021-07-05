package com.someapp.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Relationship extends AbstractPersistable<UUID> {

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    private UUID actionUserId;

    // STATUS CODES: 0 Pending, 1 Accepted, 2 Declined, 3 Blocked
    private int status;

    @CreationTimestamp
    private Timestamp createdDate;

    public Relationship(User user1, User user2, UUID actionUserId, int status) {
        this.user1 = user1;
        this.user2 = user2;
        this.actionUserId = actionUserId;
        this.status = status;
    }

    public Relationship() {};

    public UUID getActionUserId() {
        return actionUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;
    }

    public User getUser1() { return user1; }

    public User getUser2() { return user2; }

}
