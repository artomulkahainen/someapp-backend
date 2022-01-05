package com.someapp.backend.validators;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RelationshipValidator {

    @Autowired
    RelationshipRepository relationshipRepository;

    public RelationshipValidator() {
    }

    public boolean isActiveRelationship(UUID userId1, UUID userId2) {
        List<Relationship> matches = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship ->
                        (relationship.getUser1().getUUID().equals(userId1) && relationship.getUser2().getUUID().equals(userId2)) ||
                                (relationship.getUser2().getUUID().equals(userId1) && relationship.getUser1().getUUID().equals(userId2)))
                .collect(Collectors.toList());

        if (matches.size() > 0 && matches.get(0).getStatus() == 1) {
            return true;
        }

        return false;
    }

    public boolean isUserInActiveRelationship(UUID actionUserId, Relationship relationship) {
        return (relationship.getUser1().getUUID().equals(actionUserId) || relationship.getUser2().getUUID().equals(actionUserId))
                && relationship.getStatus() == 1;
    }
}
