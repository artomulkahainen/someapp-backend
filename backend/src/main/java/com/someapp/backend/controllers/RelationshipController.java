package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.ModifyRelationshipRequest;
import com.someapp.backend.util.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class RelationshipController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @PostMapping("/saveNewRelationshipByUsingPOST")
    public Relationship saveNewRelationship(HttpServletRequest req,
                                            @Valid @RequestBody NewRelationshipRequest relationshipRequest,
                                            BindingResult bindingResult) throws BindException {

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

            // If relationship is already created, throw an error
        } else if (!relationshipRepository
                .findAll()
                .stream()
                .filter(relationship -> {
                    if (relationship.getUser1().getId().equals(actionUserId)
                            && relationship.getUser2().getId().equals(relationshipRequest.getAddedUserId())) {
                        return true;
                    } else if (relationship.getUser1().getId().equals(relationshipRequest.getAddedUserId())
                            && relationship.getUser2().getId().equals(actionUserId)) {
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList())
                .isEmpty()) {
                    throw new BadArgumentException("Relationship is already created.");

        // If both user ids are found, create new pending relationship
        } else if (userRepository.findById(actionUserId).isPresent()
                && userRepository.findById(relationshipRequest.getAddedUserId()).isPresent()) {
            return relationshipRepository.save(new Relationship(
                            userRepository.getById(actionUserId),
                            userRepository.getById(relationshipRequest.getAddedUserId()),
                            actionUserId, 0));

        // If either user id is not found, throw an error
        } else {
            throw new ResourceNotFoundException("Either user id was not found");
        }
    }

    @PutMapping("/updateRelationshipByUsingPUT")
    public Relationship updateRelationship(HttpServletRequest req,
                                           @Valid @RequestBody ModifyRelationshipRequest modifyRelationshipRequest,
                                           BindingResult bindingResult) throws BindException {

        UUID modifyingUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Optional<Relationship> relationship = relationshipRepository
                .findById(modifyRelationshipRequest.getRelationshipId());

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

        // If relationship is found, try to modify it
        } else if (relationship.isPresent()) {

            // Confirm that relationship actionUserId != modifying userId
            // Check also that modifying user belongs to relationship
            if (!relationship.get().getActionUserId().equals(modifyingUserId) &&
                    (modifyingUserId.equals(relationship.get().getUser1().getId())
                            || modifyingUserId.equals(relationship.get().getUser2().getId()))) {

                // Finally modify the new status for the relationship and save it
                relationship.get().setStatus(modifyRelationshipRequest.getStatus());
                return relationshipRepository.save(relationship.get());

                // Or throw an error
            } else {
                throw new BadArgumentException("Modifying user has no permits to modify the relationship.");
            }

        // If relationship is not found, throw an error
        } else {
            throw new ResourceNotFoundException("Relationship not found with given uuid.");
        }
    }
}
