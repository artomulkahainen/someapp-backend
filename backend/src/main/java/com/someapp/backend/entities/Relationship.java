package com.someapp.backend.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Relationship extends AbstractPersistable<UUID> {

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private UUID relationshipWith;

    /**
     * unique id is in form "771adf31-3ac3-4703-881a-e40ecf6e1134,316d7af3-3f53-40a5-bdbd-8db5b9e301a7" of two uuids separated with comma
     * first uuid is action user's id, second id belongs to non-action user id
     * Action user is the one who created the relationship (by sending friend invite)
     * Non-action user is the one who is responding to friend invite
     */
    @NotNull
    private String uniqueId;

    // STATUS CODES: 0 Pending, 1 Accepted, 2 Blocked
    @Min(0)
    @Max(2)
    private int status;

    @Column(name = "createdDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    // Remove if not needed for repository
    public UUID getUserId() {
        return user.getUUID();
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
