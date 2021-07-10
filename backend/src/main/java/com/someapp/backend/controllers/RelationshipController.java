package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.services.RelationshipServiceImpl;
import com.someapp.backend.util.requests.ModifyRelationshipRequest;
import com.someapp.backend.util.requests.NewRelationshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RelationshipController {

    @Autowired
    private RelationshipServiceImpl relationshipService;

    @PostMapping("/saveNewRelationshipByUsingPOST")
    public Relationship saveNewRelationship(HttpServletRequest req,
                                            @Valid @RequestBody NewRelationshipRequest relationshipRequest,
                                            BindingResult bindingResult) throws BindException {
        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return relationshipService.save(req, relationshipRequest);
    }

    @PutMapping("/updateRelationshipByUsingPUT")
    public Relationship updateRelationship(HttpServletRequest req,
                                           @Valid @RequestBody ModifyRelationshipRequest modifyRelationshipRequest,
                                           BindingResult bindingResult) throws BindException {

        // If validation errors, throw an error
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return relationshipService.update(req, modifyRelationshipRequest);
    }
}
