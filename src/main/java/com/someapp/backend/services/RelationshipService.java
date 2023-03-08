package com.someapp.backend.services;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.dto.StatusResponse;
import com.someapp.backend.entities.Relationship;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelationshipService {

    Relationship save(SaveRelationshipDTO saveRelationshipDTO);

    boolean usersHaveActiveRelationship(String uniqueId);

    List<Relationship> findUsersRelationships(UUID userId);

    Optional<Relationship> findRelationshipById(UUID uuid);

    List<Relationship> findRelationshipsByUniqueId(String uniqueId);

    StatusResponse declineRelationshipRequest(String uniqueId,
                                              UUID declinerUUID);
}
