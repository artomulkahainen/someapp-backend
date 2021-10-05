package com.someapp.backend.controllers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.services.RelationshipServiceImpl;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.mappers.RelationshipMapper;
import com.someapp.backend.util.requests.ModifyRelationshipRequest;
import com.someapp.backend.util.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class RelationshipController {

    @Autowired
    private RelationshipServiceImpl relationshipService;

    private RelationshipMapper relationshipMapper;

    private JWTTokenUtil jwtTokenUtil;

    @GetMapping("/getUserRelationshipsByUsingGET")
    public List<RelationshipDTO> getUserRelationships(HttpServletRequest req) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        List<Relationship> relationships = relationshipService.getRelationships(req);

        return relationships
                .stream()
                .map(relationship -> relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId))
                .collect(ImmutableList.toImmutableList());
    }

    @PostMapping("/saveNewRelationshipByUsingPOST")
    public RelationshipDTO saveNewRelationship(HttpServletRequest req,
                                            @Valid @RequestBody NewRelationshipRequest relationshipRequest,
                                            BindingResult bindingResult) throws BindException {
        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Relationship relationship = relationshipService.save(req, relationshipRequest);

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId);
    }

    @PutMapping("/updateRelationshipByUsingPUT")
    public RelationshipDTO updateRelationship(HttpServletRequest req,
                                           @Valid @RequestBody ModifyRelationshipRequest modifyRelationshipRequest,
                                           BindingResult bindingResult) throws BindException {

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Relationship relationship = relationshipService.update(req, modifyRelationshipRequest);

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationship, actionUserId);
    }
}
