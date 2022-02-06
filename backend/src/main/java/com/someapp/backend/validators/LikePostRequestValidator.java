package com.someapp.backend.validators;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class LikePostRequestValidator implements Validator {

    private final PostLikeService postLikeService;
    private final RelationshipService relationshipService;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest req;

    public LikePostRequestValidator(PostLikeService postLikeService,
                                    RelationshipService relationshipService,
                                    JWTTokenUtil jwtTokenUtil) {
        this.postLikeService = postLikeService;
        this.relationshipService = relationshipService;
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
        if (!relationshipService.usersHaveActiveRelationship(actionUserId, likePostRequest.getPostUserId())) {
            errors.reject("Action user and post creator user doesn't have active relationship");
        }

        // IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, REJECT
        if (postLikeService.likeAlreadyExists(actionUserId, likePostRequest)) {
            errors.reject("Post is already liked by the action user");
        }
    }
}
