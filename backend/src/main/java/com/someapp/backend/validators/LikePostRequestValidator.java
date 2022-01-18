package com.someapp.backend.validators;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
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
public class LikePostRequestValidator implements Validator {

    private final PostLikeRepository postLikeRepository;
    private final RelationshipRepository relationshipRepository;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest req;

    public LikePostRequestValidator(PostLikeRepository postLikeRepository,
                                    RelationshipRepository relationshipRepository,
                                    JWTTokenUtil jwtTokenUtil) {
        this.postLikeRepository = postLikeRepository;
        this.relationshipRepository = relationshipRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LikePostRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LikePostRequest likePostRequest = (LikePostRequest) target;
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        // IF ACTION USER AND USER WHO MADE THE POST ARE NOT FRIENDS, REJECT
        if (!isActiveRelationship(actionUserId, likePostRequest.getPostUserId())) {
            errors.reject("Action user and post creator user doesn't have active relationship");
        }

        // IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, REJECT
        if (likeAlreadyExists(actionUserId, likePostRequest)) {
            errors.reject("Post is already liked by the action user");
        }

    }

    public boolean isActiveRelationship(UUID userId1, UUID userId2) {
        List<Relationship> matches = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship ->
                        (relationship.getUser1().getUUID().equals(userId1) && relationship.getUser2().getUUID().equals(userId2)) ||
                                (relationship.getUser2().getUUID().equals(userId1) && relationship.getUser1().getUUID().equals(userId2)))
                .collect(Collectors.toList());

        if (matches.size() > 0 && matches.get(0).getStatus() == 1) {
            return true;
        }

        return false;
    }

    private boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return !postLikeRepository
                .findByUserUUIDAndPostUUID(actionUserId, likePostRequest.getPostId()).isPresent();
    }
}
