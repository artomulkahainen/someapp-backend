package com.someapp.backend.validators;

import com.someapp.backend.dto.DeclineRelationshipRequest;
import com.someapp.backend.dto.DeletePostRequest;
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
import java.util.UUID;

@Component
public class DeclineRelationshipRequestValidator implements Validator {

    private final RelationshipService relationshipService;
    private final JWTTokenUtil jwtTokenUtil;

    public DeclineRelationshipRequestValidator(
            final RelationshipService relationshipService,
            final JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return DeletePostRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target,
                         final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final DeclineRelationshipRequest request =
                (DeclineRelationshipRequest) target;
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        final List<Relationship> relationships =
                relationshipService.findRelationshipsByUniqueId(
                        request.getRelationshipUniqueId());

        // IF RELATIONSHIP IS NOT FOUND, REJECT
        if (relationships.size() < 2) {
            errors.reject(
                    "Relationship was" +
                            " not found with given uniqueId");
        }

        // CAN DECLINE ONLY OWN RELATIONSHIPS
        if (!request.getRelationshipUniqueId()
                .contains(actionUserId.toString())) {
            errors.reject("Only own relationships" +
                    " can be declined.");
        }
    }


}
