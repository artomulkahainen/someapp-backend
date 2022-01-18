package com.someapp.backend.controllers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.api.RelationshipApi;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.utils.requests.ModifyRelationshipRequest;
import com.someapp.backend.utils.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
public class RelationshipController implements RelationshipApi {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    private JWTTokenUtil jwtTokenUtil;

    @Override
    public List<RelationshipDTO> getUserRelationships(HttpServletRequest req) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        List<Relationship> relationships = relationshipService.getRelationships(req);

        return relationships
                .stream()
                .map(relationship -> relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public RelationshipDTO saveNewRelationship(HttpServletRequest req,
                                               NewRelationshipRequest relationshipRequest,
                                               BindingResult bindingResult) throws BindException {
        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        Relationship relationship = relationshipService.save(req, relationshipRequest);

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId);
    }

    @Override
    public RelationshipDTO updateRelationship(HttpServletRequest req,
                                              ModifyRelationshipRequest modifyRelationshipRequest,
                                              BindingResult bindingResult) throws BindException {

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        Relationship relationship = relationshipService.update(req, modifyRelationshipRequest);

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId);
    }
}
