package com.someapp.backend.validators;

import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return RelationshipDTO.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        RelationshipDTO dto = (RelationshipDTO) target;
        Optional<Relationship> existingRelationship = relationshipService.findRelationshipById(dto.getUuid().orElse(null));
        UUID currentUserId = jwtTokenUtil.getIdFromToken(req);

        if (dto.getStatus() == 0) {
            /**
             * If relationship is not found from repository, only pending status (0) is allowed.
             * Existing relationship cannot be changed to pending status (0).
             * Action user can send only status 0 (pending requests).
             */
            validateStatusZero(dto, errors, currentUserId);
        }


    }

    private boolean relationshipExists(RelationshipDTO relationshipDTO) {
        String reversedId = relationshipDTO.getNonActionUserId() + "," + relationshipDTO.getActionUserId();
        List<Relationship> relationships = relationshipService.findRelationshipsByUniqueId(relationshipDTO.getUniqueId());
        List<Relationship> rs = relationshipService.findRelationshipsByUniqueId(reversedId);

        return !relationships.isEmpty() || !rs.isEmpty();
    }

    private void validateStatusZero(RelationshipDTO dto, Errors errors, UUID currentUserId) {
        if (!relationshipExists(dto) && dto.getStatus() != 0) {
            errors.reject("Only status 0 is allowed for new relationship");
        }

        if (relationshipExists(dto)) {
            errors.reject("Existing relationship cannot be changed to pending");
        }

        if (!dto.getActionUserId().equals(currentUserId)) {
            errors.reject("The one who creates friend invite can only send pending invites");
        }
    }

    /**
     *
     * Action user can send only status 0 (pending requests)
     */

    /**
     * Non-action user can only send status 1 (accept request) or status 2 (block user)
     */

    /**
     * If relationship with given uniqueId is already found (even reversed uniqueId), prevent saving
     */

    /**
     * Accepted relationship cannot be changed to pending or blocked
     */


}
