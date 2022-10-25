package com.someapp.backend.controllers;

import com.someapp.backend.api.RelationshipApi;
import com.someapp.backend.dto.DeclineRelationshipRequest;
import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.dto.StatusResponse;
import com.someapp.backend.mappers.RelationshipMapper;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.validators.DeclineRelationshipRequestValidator;
import com.someapp.backend.validators.SaveRelationshipDTOValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@RestController
public class RelationshipController implements RelationshipApi {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private SaveRelationshipDTOValidator saveValidator;

    @Autowired
    private DeclineRelationshipRequestValidator declineValidator;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public RelationshipDTO save(final SaveRelationshipDTO saveRelationshipDTO,
                                final BindingResult bindingResult) throws BindException {
        saveValidator.validate(saveRelationshipDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return relationshipMapper.mapRelationshipToRelationshipDTO(relationshipService.save(saveRelationshipDTO));
    }

    @Override
    public StatusResponse decline(final DeclineRelationshipRequest declineRelationshipRequest,
                                  final BindingResult bindingResult) throws BindException {
        declineValidator.validate(declineRelationshipRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final UUID declinerUUID = jwtTokenUtil.getIdFromToken(req);

        return relationshipService.declineRelationshipRequest(
                declineRelationshipRequest.getRelationshipUniqueId(), declinerUUID);
    }
}
