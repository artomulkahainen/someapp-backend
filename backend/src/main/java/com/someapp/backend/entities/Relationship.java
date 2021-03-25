/*package com.someapp.backend.entities;

import com.someapp.backend.util.embeddedkeys.RelationshipKey;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class Relationship {

    @EmbeddedId
    @NotNull
    private RelationshipKey relationshipKey;

    @ManyToMany
    @NotNull
    private List<User> userList;

    @NotNull
    private UUID actionUserId;

    // STATUS CODES: 0 Pending, 1 Accepted, 2 Declined, 3 Blocked
    @NotNull
    private int status;

    @CreationTimestamp
    private Timestamp timestamp;

    public Relationship(List<User> userList, UUID actionUserId, int status) {
        this.relationshipKey = new RelationshipKey(userList.get(0).getId(), userList.get(1).getId());
        this.userList = userList;
        this.actionUserId = actionUserId;
        this.status = status;
    }

    public UUID getActionUserId() {
        return actionUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public RelationshipKey getRelationshipKey() {
        return relationshipKey;
    }

    public void setRelationshipKey(RelationshipKey relationshipKey) {
        this.relationshipKey = relationshipKey;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
*/