package com.someapp.backend.interfaces.repositories;

import com.someapp.backend.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
}
