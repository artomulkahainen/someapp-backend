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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class RelationshipMapper {

    private final RelationshipService relationshipService;
    private final ExtendedUserDetailsService userService;
    private final JWTTokenUtil jwtTokenUtil;

    public RelationshipMapper(final RelationshipService relationshipService,
                              final ExtendedUserDetailsService userService,
                              final JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public RelationshipDTO mapRelationshipToRelationshipDTO(final Relationship relationship) {
        return new RelationshipDTO(relationship.getUser().getUUID(),
                relationship.getRelationshipWith(),
                relationship.getUniqueId(),
                relationship.getStatus(),
                relationship.getCreatedDate(),
                relationship.getId());
    }

    public List<RelationshipDTO> mapRelationshipsToRelationshipDTOs(final List<Relationship> relationships) {
        return relationships.stream()
                .map(relationship -> mapRelationshipToRelationshipDTO(relationship))
                .collect(ImmutableList.toImmutableList());
    }

    public Relationship mapSaveRelationshipDTOToRelationship(final SaveRelationshipDTO saveRelationshipDTO,
                                                             final boolean saveOther) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final List<Relationship> relationshipsByUniqueId = relationshipService
                .findRelationshipsByUniqueId(saveRelationshipDTO.getUniqueId());

        // find relationship with given unique id or create new relationship
        final Relationship relationship = relationshipsByUniqueId
                .stream()
                .filter(rs -> jwtTokenUtil.getIdFromToken(req).equals(saveOther
                        ? rs.getRelationshipWith() : rs.getUser().getUUID()))
                .findFirst().orElse(new Relationship());

        // If relationship is new
        if (relationship.getUUID() == null) {
            final User user = userService.findUserById(saveOther
                            ? saveRelationshipDTO.getRelationshipWithId() : jwtTokenUtil.getIdFromToken(req))
                    .orElseThrow(ResourceNotFoundException::new);
            relationship.setUser(user);
            relationship.setRelationshipWith(saveOther
                    ? jwtTokenUtil.getIdFromToken(req) : saveRelationshipDTO.getRelationshipWithId());
            relationship.setUniqueId(saveRelationshipDTO.getUniqueId());
        }

        relationship.setStatus(saveRelationshipDTO.getStatus());
        return relationship;
    }
}
