package com.someapp.backend.services;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.utils.customExceptions.ResourceNotFoundException;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.ModifyRelationshipRequest;
import com.someapp.backend.utils.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Override
    public List<Relationship> getRelationships(HttpServletRequest req) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        return relationshipRepository
                .findAll()
                .stream()
                .filter(relationship -> relationship.getUser1().equals(actionUserId) || relationship.getUser2().equals(actionUserId))
                .collect(Collectors.toList());
    }

    @Override
    public Relationship save(HttpServletRequest req, NewRelationshipRequest relationshipRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        // If relationship is already created, throw an exception
        if (relationshipAlreadyExists(actionUserId, relationshipRequest)) {
            throw new BadArgumentException("Relationship is already created.");

            // If either user id is not found, throw an exception
        } else if (!usersFound(actionUserId, relationshipRequest.getAddedUserId())) {
            throw new ResourceNotFoundException("Either/both user id was not found");

            // If both user ids are found, create new pending relationship
        } else {
            return relationshipRepository.save(new Relationship(
                    userRepository.getById(actionUserId),
                    userRepository.getById(relationshipRequest.getAddedUserId()),
                    actionUserId, 0));
        }
    }

    @Override
    public Relationship update(HttpServletRequest req, ModifyRelationshipRequest modifyRelationshipRequest) {
        UUID modifyingUserId = jwtTokenUtil.getIdFromToken(req);
        Optional<Relationship> relationship = relationshipRepository
                .findById(modifyRelationshipRequest.getRelationshipId());

        // If relationship is not found, throw an exception
        if (!relationship.isPresent()) {
            throw new ResourceNotFoundException("Relationship not found with given uuid.");

            // If relationship is found, try to modify it
        } else {
            // Confirm that relationship actionUserId != modifying userId
            // Check also that modifying user belongs to relationship
            if (userIsValidToModify(modifyingUserId, relationship)) {

                // Finally modify the new status for the relationship and save it
                relationship.get().setStatus(modifyRelationshipRequest.getStatus());
                return relationshipRepository.save(relationship.get());

                // Or throw an error
            } else {
                throw new BadArgumentException("Modifying user has no permits to modify the relationship.");
            }
        }
    }

    public boolean usersHaveActiveRelationship(UUID userId, UUID userId2) {
        List<Relationship> matches = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship ->
                        (relationship.getUser1().getUUID().equals(userId) && relationship.getUser2().getUUID().equals(userId2)) ||
                                (relationship.getUser2().getUUID().equals(userId) && relationship.getUser1().getUUID().equals(userId2)))
                .collect(Collectors.toList());
        return !matches.isEmpty() && matches.get(0).getStatus() == 1;
    }

    private boolean relationshipAlreadyExists(UUID actionUserId, NewRelationshipRequest relationshipRequest) {
        return !relationshipRepository
                .findAll()
                .stream()
                .anyMatch(relationship -> {
                    if (relationship.getUser1().getId().equals(actionUserId)
                            && relationship.getUser2().getId().equals(relationshipRequest.getAddedUserId())) {
                        return true;
                    } else if (relationship.getUser1().getId().equals(relationshipRequest.getAddedUserId())
                            && relationship.getUser2().getId().equals(actionUserId)) {
                        return true;
                    }

                    return false;
                });
    }

    private boolean usersFound(UUID user, UUID user2) {
        return userRepository.findById(user).isPresent()
                && userRepository.findById(user2).isPresent();
    }

    private boolean userIsValidToModify(UUID modifyingUserId, Optional<Relationship> relationship) {
        return !relationship.get().getActionUserId().equals(modifyingUserId) &&
                (modifyingUserId.equals(relationship.get().getUser1().getId())
                        || modifyingUserId.equals(relationship.get().getUser2().getId()));
    }
}
