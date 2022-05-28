package com.someapp.backend.services;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.dto.StatusResponse;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    private RelationshipMapper relationshipMapper;

    @Autowired
    public RelationshipServiceImpl(@Lazy RelationshipMapper relationshipMapper) {
        this.relationshipMapper = relationshipMapper;
    }

    @Override
    @Transactional
    public Relationship save(SaveRelationshipDTO saveRelationshipDTO) {
        String uniqueId = saveRelationshipDTO.getUniqueId();
        boolean existingBlockedRelationship = relationshipRepository.findRelationshipsByUniqueId(uniqueId).size() > 0
                && relationshipRepository.findRelationshipsByUniqueId(uniqueId).get(0).getStatus() == 2;

        // If save is not block request or if other user haven't blocked invite sender already
        if (saveRelationshipDTO.getStatus() != 2 && !existingBlockedRelationship) {
            /**
             *   Saving relationship is splitted into two different actions, if status is either 0 or 1
             */

            // First save the relationshipWith user's relationship
            Relationship otherUsersRelationship = relationshipMapper
                    .mapSaveRelationshipDTOToRelationship(saveRelationshipDTO, false);
            relationshipRepository.save(otherUsersRelationship);
        }

        return relationshipRepository.save(
                relationshipMapper.mapSaveRelationshipDTOToRelationship(saveRelationshipDTO, true));
    }

    @Override
    @Transactional
    public StatusResponse declineRelationshipRequest(String uniqueId) {
        return new StatusResponse(200);
    }

    @Override
    public List<Relationship> findRelationshipsByUniqueId(String uniqueId) {
        return relationshipRepository.findRelationshipsByUniqueId(uniqueId);
    }

    @Override
    public Optional<Relationship> findRelationshipById(UUID uuid) {
        return uuid != null ? relationshipRepository.findById(uuid) : Optional.empty();
    }

    @Override
    public boolean usersHaveActiveRelationship(String uniqueId) {
        List<Relationship> matches = findRelationshipsByUniqueId(uniqueId);
        return matches.size() == 2 && matches.get(0).getStatus() == 1 && matches.get(1).getStatus() == 1;
    }
}
