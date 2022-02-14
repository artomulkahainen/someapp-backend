package com.someapp.backend.mappers;

import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RelationshipMapper {

    private RelationshipService relationshipService;
    private ExtendedUserDetailsService userService;
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public RelationshipMapper(RelationshipService relationshipService,
                              ExtendedUserDetailsService userService,
                              JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public RelationshipDTO mapRelationshipToRelationshipDTO(Relationship relationship) {
        UUID currentUserId = jwtTokenUtil.getIdFromToken(req);
        UUID relationshipWithId = relationship.getActionUserId().equals(currentUserId)
                ? relationship.getNonActionUserId() : relationship.getActionUserId();

        return new RelationshipDTO(relationshipWithId, relationship.getUniqueId(),
                relationship.getStatus(), Optional.of(relationship.getId()), Optional.of(relationship.getCreatedDate()));
    }

    public Relationship mapRelationshipDTOToRelationship(RelationshipDTO relationshipDTO) {
        Relationship relationship = relationshipService.findRelationshipById(relationshipDTO.getUuid().orElse(null))
                .orElse(new Relationship());

        if (!relationshipDTO.getUuid().isPresent()) {
            User user = userService.findUserById(jwtTokenUtil.getIdFromToken(req))
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(relationshipDTO.getRelationshipWithId());
            relationship.setUniqueId(relationshipDTO.getUniqueId());
        }
        relationship.setStatus(relationshipDTO.getStatus());
        return relationship;
    }

    public Relationship mapOtherUsersRelationshipDTOToRelationship(RelationshipDTO relationshipDTO, UUID otherUserId, boolean isActionUser) {
        List<Relationship> relationshipsByUniqueId = relationshipService.findRelationshipsByUniqueId(relationshipDTO.getUniqueId());
        Relationship relationship = relationshipsByUniqueId
                .stream()
                .filter(rs -> isActionUser ? rs.getNonActionUserId().equals(otherUserId) : rs.getActionUserId().equals(otherUserId))
                .findFirst().orElse(new Relationship());

        if (relationshipsByUniqueId.isEmpty()) {
            User user = userService.findUserById(otherUserId)
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(relationshipDTO.getRelationshipWithId());
            relationship.setUniqueId(relationshipDTO.getUniqueId());
        }
        relationship.setStatus(relationshipDTO.getStatus());
        return relationship;
    }

}
