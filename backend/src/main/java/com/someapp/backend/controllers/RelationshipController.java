package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Relation;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class RelationshipController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @GetMapping("/relationship/{userId}")
    public List<Relationship> getUserRelationships(@PathVariable UUID userId) {
        return relationshipRepository
                .findAll()
                .stream()
                .filter(relationship ->
                        relationship.getUser1().getId().equals(userId) ||
                                relationship.getUser2().getId().equals(userId))
                .collect(Collectors.toList());
    }
}
