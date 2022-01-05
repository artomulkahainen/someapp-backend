package com.someapp.backend.mappers;

import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RelationshipMapper {

    public RelationshipDTO mapRelationshipToRelationshipDTO(Relationship relationship, UUID userId) {
        return new RelationshipDTO(
                relationship.getUUID(),
                relationship.getCreatedDate(),
                relationship.getUser1().getUUID() == userId
                        ? relationship.getUser1().getUUID() : relationship.getUser2().getUUID(),
                relationship.getActionUserId(),
                relationship.getStatus()
        );
    }
}
