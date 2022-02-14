package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.ModifyRelationshipRequest;
import com.someapp.backend.utils.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
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
    private RelationshipMapper relationshipMapper;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    @Override
    @Transactional
    public Relationship save(RelationshipDTO relationshipDTO) {
        boolean isActionUser = isActionUser(relationshipDTO);
        String uniqueId = relationshipDTO.getUniqueId();

        // If save is not block request
        if (relationshipDTO.getStatus() != 2) {
            /**
             *   Saving relationship is splitted into two different actions, if status is either 0 or 1
             */

            // First save the other user's relationship
            Relationship otherUsersRelationship = relationshipMapper
                    .mapOtherUsersRelationshipDTOToRelationship(
                            relationshipDTO,
                            isActionUser ? getNonActionUserIdFromUniqueId(uniqueId) : getActionUserIdFromUniqueId(uniqueId),
                            isActionUser);
            relationshipRepository.save(otherUsersRelationship);
        }

        return relationshipRepository.save(
                relationshipMapper.mapRelationshipDTOToRelationship(relationshipDTO));
    }

    @Override
    @Transactional
    public DeleteResponse declineRelationshipRequest(String uniqueId) {
        return new DeleteResponse("");
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

    private boolean isActionUser(RelationshipDTO relationshipDTO) {
        return Objects.equals(getActionUserIdFromUniqueId(relationshipDTO.getUniqueId()), jwtTokenUtil.getIdFromToken(req));
    }

    private UUID getActionUserIdFromUniqueId(String uniqueId) {
        return UUID.fromString(uniqueId.split(",")[0]);
    }

    private UUID getNonActionUserIdFromUniqueId(String uniqueId) {
        return UUID.fromString(uniqueId.split(",")[1]);
    }
}
