package com.someapp.backend.controllers;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.dto.abstractDTOs.RelationshipDTO;
import com.someapp.backend.api.RelationshipApi;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.validators.SaveRelationshipDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationshipController implements RelationshipApi {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private SaveRelationshipDTOValidator saveValidator;

    private JWTTokenUtil jwtTokenUtil;

    @Override
    public RelationshipDTO save(SaveRelationshipDTO saveRelationshipDTO, BindingResult bindingResult) throws BindException {
        saveValidator.validate(saveRelationshipDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationshipService.save(saveRelationshipDTO));
    }
}
