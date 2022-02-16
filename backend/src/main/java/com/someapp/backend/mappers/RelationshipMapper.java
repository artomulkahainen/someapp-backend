package com.someapp.backend.mappers;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.dto.abstractDTOs.RelationshipDTO;
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
        return new RelationshipDTO(relationship.getUser().getUUID(),
                relationship.getRelationshipWith(),
                relationship.getUniqueId(),
                relationship.getStatus(),
                relationship.getCreatedDate(),
                relationship.getId());
    }

    public Relationship mapSaveRelationshipDTOToRelationship(SaveRelationshipDTO saveRelationshipDTO) {
        List<Relationship> relationshipsByUniqueId = relationshipService.findRelationshipsByUniqueId(saveRelationshipDTO.getUniqueId());

        // find own relationship with given unique id or create new relationship
        Relationship relationship = relationshipsByUniqueId
                .stream()
                .filter(rs -> jwtTokenUtil.getIdFromToken(req).equals(rs.getUser().getUUID()))
                .findFirst().orElse(new Relationship());

        // If relationship is new
        if (relationship.getUUID() == null) {
            User user = userService.findUserById(jwtTokenUtil.getIdFromToken(req))
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(saveRelationshipDTO.getRelationshipWithId());
            relationship.setUniqueId(saveRelationshipDTO.getUniqueId());
        }
        relationship.setStatus(saveRelationshipDTO.getStatus());
        return relationship;
    }

    public Relationship mapOtherUsersRelationshipDTOToRelationship(SaveRelationshipDTO saveRelationshipDTO) {
        List<Relationship> relationshipsByUniqueId = relationshipService.findRelationshipsByUniqueId(saveRelationshipDTO.getUniqueId());

        // find relationshipWith user's relationship (with same uniqueId) or create new relationship
        Relationship relationship = relationshipsByUniqueId
                .stream()
                .filter(rs -> jwtTokenUtil.getIdFromToken(req).equals(rs.getRelationshipWith()))
                .findFirst().orElse(new Relationship());

        // If relationship is new
        if (relationship.getUUID() == null) {
            User user = userService.findUserById(saveRelationshipDTO.getRelationshipWithId())
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(jwtTokenUtil.getIdFromToken(req));
            relationship.setUniqueId(saveRelationshipDTO.getUniqueId());
        }
        relationship.setStatus(saveRelationshipDTO.getStatus());
        return relationship;
    }

}
