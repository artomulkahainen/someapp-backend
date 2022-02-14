package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.utils.requests.ModifyRelationshipRequest;
import com.someapp.backend.utils.requests.NewRelationshipRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelationshipService {

    Relationship save(RelationshipDTO relationshipDTO);

    boolean usersHaveActiveRelationship(String uniqueId);

    Optional<Relationship> findRelationshipById(UUID uuid);

    List<Relationship> findRelationshipsByUniqueId(String uniqueId);

    DeleteResponse declineRelationshipRequest(String uniqueId);
}
