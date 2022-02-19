package com.someapp.backend.interfaces.repositories;

import com.someapp.backend.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    List<Relationship> findRelationshipsByUniqueId(String uniqueId);
    List<Relationship> findRelationshipsByUserId(UUID uuid);
}
