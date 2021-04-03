package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.requests.RelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/relationship")
    public Relationship postNewRelationship(
            @Valid @RequestBody RelationshipRequest relationshipRequest,
            BindingResult bindingResult) throws BindException {

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

            // If relationship is already created, throw an error
        } else if (!relationshipRepository
                .findAll()
                .stream()
                .filter(relationship -> {
                    if (relationship.getUser1().getId().equals(relationshipRequest.getUser1Id())
                            && relationship.getUser2().getId().equals(relationshipRequest.getUser2Id())) {
                        return true;
                    } else if (relationship.getUser1().getId().equals(relationshipRequest.getUser2Id())
                            && relationship.getUser2().getId().equals(relationshipRequest.getUser1Id())) {
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList())
                .isEmpty()) {
                    throw new BadArgumentException("Relationship is already created.");

        // If both user ids are found, create new pending relationship
        } else if (userRepository.findById(relationshipRequest.getUser1Id()).isPresent()
                && userRepository.findById(relationshipRequest.getUser2Id()).isPresent()) {
            return relationshipRepository.save(new Relationship(
                            userRepository.getById(relationshipRequest.getUser1Id()),
                            userRepository.getById(relationshipRequest.getUser2Id()),
                            relationshipRequest.getActionUserId(), 0));

        // If either user id is not found, throw an error
        } else {
            throw new ResourceNotFoundException("Either user id was not found");
        }
    }
}
