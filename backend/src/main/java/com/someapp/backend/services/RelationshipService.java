package com.someapp.backend.services;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.utils.requests.ModifyRelationshipRequest;
import com.someapp.backend.utils.requests.NewRelationshipRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface RelationshipService {

    List<Relationship> getRelationships(HttpServletRequest req);

    Relationship save(HttpServletRequest req, NewRelationshipRequest relationshipRequest);

    Relationship update(HttpServletRequest req, ModifyRelationshipRequest modifyRelationshipRequest);

    boolean usersHaveActiveRelationship(UUID userId, UUID userId2);
}
