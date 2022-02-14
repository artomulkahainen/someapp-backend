package com.someapp.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Relationship extends AbstractPersistable<UUID> {

    @ManyToOne
    private User user;

    private UUID relationshipWith;

    private String uniqueId;

    // STATUS CODES: 0 Pending, 1 Accepted, 2 Declined, 3 Blocked
    private int status;

    @CreationTimestamp
    private Timestamp createdDate;

    public Relationship(User user, UUID relationshipWith, String uniqueId, int status) {
        this.user = user;
        this.relationshipWith = relationshipWith;
        this.uniqueId = uniqueId;
        this.status = status;
    }

    public Relationship() {}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUUID() {
        return this.getId();
    }

    public UUID getRelationshipWith() {
        return relationshipWith;
    }

    public void setRelationshipWith(UUID relationshipWith) {
        this.relationshipWith = relationshipWith;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Action user is the one who created the relationship (by sending friend invite)
     * @return
     */
    public UUID getActionUserId() {
        return UUID.fromString(uniqueId.split(",")[0]);
    }

    /**
     * Non-action user is the one who is responding to friend invite
     * @return
     */
    public UUID getNonActionUserId() {
        return UUID.fromString(uniqueId.split(",")[1]);
    }

}
