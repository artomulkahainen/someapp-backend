package com.someapp.backend.validators;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RelationshipDTOValidator implements Validator {

    private RelationshipService relationshipService;
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public RelationshipDTOValidator(RelationshipService relationshipService, JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean supports(Class<?> clazz) {
        return SaveRelationshipDTO.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        SaveRelationshipDTO dto = (SaveRelationshipDTO) target;
        Optional<Relationship> existingRelationship = relationshipService
                .findRelationshipsByUniqueId(dto.getUniqueId())
                .stream()
                .filter(rs -> rs.getUser().getUUID().equals(jwtTokenUtil.getIdFromToken(req)))
                .findFirst();

        UUID currentUserId = jwtTokenUtil.getIdFromToken(req);

        if (dto.getStatus() == 0) {
            /**
             * If relationship is not found from repository, only pending status (0) is allowed.
             * Existing relationship cannot be changed to pending status (0).
             * Action user can send only status 0 (pending requests).
             */
            validateStatusZero(dto, errors, currentUserId);
        } else if (dto.getStatus() == 1) {
            /**
             * Non-action user can only accept request.
             * If relationship with given uniqueId is not found, prevent saving.
             */
            validateStatusOne(dto, errors, currentUserId, existingRelationship);
        } else if (dto.getStatus() == 2) {
            /**
             * Accepted relationship cannot be changed to blocked.
             * Non-action user can only block user.
             * Cannot block relationship that doesn't exist.
             */
            validateStatusTwo(dto, errors, currentUserId, existingRelationship);
        }


    }

    private boolean relationshipExists(SaveRelationshipDTO saveRelationshipDTO) {
        String reversedId = saveRelationshipDTO.getNonActionUserId() + "," + saveRelationshipDTO.getActionUserId();
        List<Relationship> relationships = relationshipService.findRelationshipsByUniqueId(saveRelationshipDTO.getUniqueId());
        List<Relationship> rs = relationshipService.findRelationshipsByUniqueId(reversedId);

        return !relationships.isEmpty() || !rs.isEmpty();
    }

    private boolean exactRelationshipExists(SaveRelationshipDTO dto) {
        return relationshipService.findRelationshipsByUniqueId(dto.getUniqueId()).size() > 1;
    }

    private void validateStatusZero(SaveRelationshipDTO dto, Errors errors, UUID currentUserId) {
        if (!relationshipExists(dto) && dto.getStatus() != 0) {
            errors.reject("Only pending status is allowed for new relationship");
        }

        if (relationshipExists(dto)) {
            errors.reject("Existing relationship cannot be changed to pending " +
                    "or new pending relationship cannot be created if existing is found.");
        }

        if (!dto.getActionUserId().equals(currentUserId)) {
            errors.reject("The one who creates friend invite can only send pending invites");
        }
    }

    private void validateStatusOne(SaveRelationshipDTO dto, Errors errors, UUID currentUserId, Optional<Relationship> existing) {
        if (!exactRelationshipExists(dto)) {
            errors.reject("Cannot accept relationship that doesn't exist");
        } else if (existing.isPresent() && ImmutableList.of(1, 2).contains(existing.get().getStatus())) {
            errors.reject("Already accepted or blocked request cannot be changed to accepted");
        } else if (!dto.getNonActionUserId().equals(currentUserId)) {
            errors.reject("Invite recipient can only accept requests or block users");
        }
    }

    private void validateStatusTwo(SaveRelationshipDTO dto, Errors errors, UUID currentUserId, Optional<Relationship> existing) {
        if (!exactRelationshipExists(dto)) {
            errors.reject("Cannot block relationship that doesn't exist");
        } else if (!dto.getNonActionUserId().equals(currentUserId)) {
            errors.reject("Invite recipient can only accept requests or block users");
        } else if (existing.isPresent() && existing.get().getStatus() == 1) {
            errors.reject("Accepted relationship cannot be changed to blocked");
        }
    }
}
