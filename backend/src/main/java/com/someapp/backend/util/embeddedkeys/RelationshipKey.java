/*package com.someapp.backend.util.embeddedkeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class RelationshipKey implements Serializable {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "user_id_2", nullable = false)
    private UUID userId2;

    public RelationshipKey(UUID userId, UUID userId2) {
        this.userId = userId;
        this.userId2 = userId2;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId2() {
        return userId2;
    }

    public void setUserId2(UUID userId2) {
        this.userId2 = userId2;
    }

}
*/