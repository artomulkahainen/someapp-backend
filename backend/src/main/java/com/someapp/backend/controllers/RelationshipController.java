package com.someapp.backend.controllers;

import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.interfaces.api.RelationshipApi;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.validators.RelationshipDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RelationshipController implements RelationshipApi {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private RelationshipDTOValidator saveValidator;

    @Autowired
    private HttpServletRequest req;

    private JWTTokenUtil jwtTokenUtil;

    @Override
    public RelationshipDTO save(RelationshipDTO relationshipDTO, BindingResult bindingResult) throws BindException {
        saveValidator.validate(relationshipDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationshipService.save(relationshipDTO));
    }
}
