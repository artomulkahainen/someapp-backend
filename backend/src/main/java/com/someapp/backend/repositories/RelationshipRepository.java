package com.someapp.backend.repositories;

import com.someapp.backend.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    List<Relationship> findRelationshipsByUniqueId(String uniqueId);
    List<Relationship> findRelationshipsByuser_id(UUID uuid);
    void deleteRelationshipByUniqueId(String uniqueId);
}
