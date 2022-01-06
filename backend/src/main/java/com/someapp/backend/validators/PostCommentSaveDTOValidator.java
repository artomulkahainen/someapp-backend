package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostCommentSaveDTOValidator implements Validator {

    private final RelationshipRepository relationshipRepository;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public PostCommentSaveDTOValidator(RelationshipRepository relationshipRepository,
                                       JWTTokenUtil jwtTokenUtil) {
        this.relationshipRepository = relationshipRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PostCommentSaveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        final PostCommentSaveDTO postCommentSaveDTO = (PostCommentSaveDTO) target;

        // POST CREATOR AND POST COMMENTER MUST HAVE ACTIVE RELATIONSHIP
        isActiveRelationship(actionUserId, postCommentSaveDTO.getPostCreatorId(), errors);
    }

    private void isActiveRelationship(UUID actionUserId, UUID postCreatorId, Errors errors) {
        /**
         *  REPLACE THIS WITH RELATIONSHIP SERVICE'S "usersHaveActiveRelationship" METHOD
         */

        List<Relationship> matches = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship ->
                        (relationship.getUser1().getUUID().equals(actionUserId) && relationship.getUser2().getUUID().equals(postCreatorId)) ||
                                (relationship.getUser2().getUUID().equals(actionUserId) && relationship.getUser1().getUUID().equals(postCreatorId)))
                .collect(Collectors.toList());

        if (matches.size() == 0 || matches.get(0).getStatus() != 1) {
            errors.reject("Active relationship with post creator is needed to write a comment");
        }
    }
}


