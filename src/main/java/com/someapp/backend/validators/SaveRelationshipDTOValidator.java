package com.someapp.backend.validators;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SaveRelationshipDTOValidator implements Validator {

    private RelationshipService relationshipService;
    private JWTTokenUtil jwtTokenUtil;

    public SaveRelationshipDTOValidator(
            final RelationshipService relationshipService,
            final JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean supports(Class<?> clazz) {

        return SaveRelationshipDTO.class.isAssignableFrom(clazz);
    }

    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req =
                ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        final SaveRelationshipDTO dto = (SaveRelationshipDTO) target;
        final Optional<Relationship>
                existingRelationship = relationshipService
                .findRelationshipsByUniqueId(dto.getUniqueId())
                .stream()
                .filter(rs ->
                        rs.getUser().getUUID()
                                .equals(jwtTokenUtil.getIdFromToken(req)))
                .findFirst();

        final UUID currentUserId = jwtTokenUtil.getIdFromToken(req);

        if (dto.getStatus() == 0) {
            /**
             * If relationship is not found from repository,
             * only pending status (0) is allowed.
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

    private boolean relationshipExists(
            final SaveRelationshipDTO saveRelationshipDTO) {
        final String reversedId =
                saveRelationshipDTO.getNonActionUserId() + ","
                + saveRelationshipDTO.getActionUserId();
        final List<Relationship> relationships = relationshipService
                .findRelationshipsByUniqueId(
                        saveRelationshipDTO.getUniqueId());
        final List<Relationship> rs =
                relationshipService.findRelationshipsByUniqueId(reversedId);

        return !relationships.isEmpty() || !rs.isEmpty();
    }

    private boolean relationshipIsBlocked(final SaveRelationshipDTO dto) {
        final String reversedId =
                dto.getNonActionUserId() + "," + dto.getActionUserId();
        final List<Relationship> relationships =
                relationshipService.findRelationshipsByUniqueId(
                        dto.getUniqueId());
        final List<Relationship> rs =
                relationshipService.findRelationshipsByUniqueId(reversedId);

        return relationshipExists(dto)
                && (relationships.stream().anyMatch(r -> r.getStatus() == 2)
                || rs.stream().anyMatch(r -> r.getStatus() == 2));
    }

    private boolean exactRelationshipExists(final SaveRelationshipDTO dto) {
        return relationshipService
                .findRelationshipsByUniqueId(dto.getUniqueId()).size() > 1;
    }

    private void validateStatusZero(
            final SaveRelationshipDTO dto,
            final Errors errors,
            final UUID currentUserId) {
        // IF OTHER USER HAVE BLOCKED ACTION USER AND
        // ACTION USER HAVE ALREADY ONE PENDING RELATIONSHIP
        if (exactRelationshipExists(dto)) {
            errors.reject("Existing relationship cannot be " +
                    "changed to pending " +
                    "or new pending relationship cannot be created " +
                    "if existing is found.");
        } else if (relationshipExists(dto) && !relationshipIsBlocked(dto)) {
            errors.reject("Existing relationship cannot " +
                    "be changed to pending " +
                    "or new pending relationship cannot be created " +
                    "if existing is found.");
        } else if (!dto.getActionUserId().equals(currentUserId)) {
            errors.reject("The one who creates friend " +
                    "invite can only send pending invites");
        }
    }

    private void validateStatusOne(final SaveRelationshipDTO dto,
                                   final Errors errors,
                                   final UUID currentUserId,
                                   final Optional<Relationship> existing) {
        if (!exactRelationshipExists(dto)) {
            errors.reject("Cannot accept relationship " +
                    "that doesn't exist");
        } else if (existing.isPresent() && ImmutableList.of(1, 2)
                .contains(existing.get().getStatus())) {
            errors.reject(
                    "Already accepted or blocked request " +
                            "cannot be changed to accepted");
        } else if (!dto.getNonActionUserId().equals(currentUserId)) {
            errors.reject("Invite recipient can only accept" +
                    " requests or block users");
        }
    }

    private void validateStatusTwo(final SaveRelationshipDTO dto,
                                   final Errors errors,
                                   final UUID currentUserId,
                                   final Optional<Relationship> existing) {
        if (!exactRelationshipExists(dto)) {
            errors.reject("Cannot block relationship" +
                    " that doesn't exist");
        } else if (!dto.getNonActionUserId().equals(currentUserId)) {
            errors.reject("Invite recipient can only " +
                    "accept requests or block users");
        } else if (existing.isPresent() && existing.get().getStatus() == 1) {
            errors.reject("Accepted relationship cannot be" +
                    " changed to blocked");
        }
    }
}
