package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

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

    public List<RelationshipDTO> mapRelationshipsToRelationshipDTOs(List<Relationship> relationships) {
        return relationships.stream()
                .map(relationship -> mapRelationshipToRelationshipDTO(relationship))
                .collect(ImmutableList.toImmutableList());
    }

    public Relationship mapSaveRelationshipDTOToRelationship(SaveRelationshipDTO saveRelationshipDTO, boolean isRelationshipCreator) {
        List<Relationship> relationshipsByUniqueId = relationshipService.findRelationshipsByUniqueId(saveRelationshipDTO.getUniqueId());

        // find relationship with given unique id or create new relationship
        Relationship relationship = relationshipsByUniqueId
                .stream()
                .filter(rs -> jwtTokenUtil.getIdFromToken(req).equals(isRelationshipCreator
                        ? rs.getUser().getUUID() : rs.getRelationshipWith()))
                .findFirst().orElse(new Relationship());

        // If relationship is new
        if (relationship.getUUID() == null) {
            User user = userService.findUserById(isRelationshipCreator
                            ? jwtTokenUtil.getIdFromToken(req) : saveRelationshipDTO.getRelationshipWithId())
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(isRelationshipCreator
                    ? saveRelationshipDTO.getRelationshipWithId() : jwtTokenUtil.getIdFromToken(req));
            relationship.setUniqueId(saveRelationshipDTO.getUniqueId());
        }

        relationship.setStatus(saveRelationshipDTO.getStatus());
        return relationship;
    }
}
