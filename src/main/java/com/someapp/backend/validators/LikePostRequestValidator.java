package com.someapp.backend.validators;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
public class LikePostRequestValidator implements Validator {

    private final PostLikeService postLikeService;
    private final PostRepository postRepository;
    private final RelationshipService relationshipService;
    private final JWTTokenUtil jwtTokenUtil;

    public LikePostRequestValidator(final PostLikeService postLikeService,
                                    final PostRepository postRepository,
                                    final RelationshipService relationshipService,
                                    final JWTTokenUtil jwtTokenUtil) {
        this.postLikeService = postLikeService;
        this.postRepository = postRepository;
        this.relationshipService = relationshipService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LikePostRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final LikePostRequest likePostRequest = (LikePostRequest) target;
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        /**
         * REJECT, IF ACTION USER AND POST CREATOR ARE NOT FRIENDS
         * UNLESS LIKING OWN POST
         */

        if (!isOwnPost(likePostRequest.getPostId(), actionUserId)
                && !relationshipService.usersHaveActiveRelationship(
                        actionUserId.toString() + "," + likePostRequest.getPostUserId())) {
            errors.reject("Action user and post creator user doesn't have active relationship");
        }

        /**
         * IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, REJECT
         */

        if (postLikeService.likeAlreadyExists(actionUserId, likePostRequest)) {
            errors.reject("Post is already liked by the action user");
        }
    }

    private boolean isOwnPost(final UUID postId, final UUID actionUserId) {
        final Optional<Post> post = postRepository.findById(postId);
        return post.isPresent() && post.get().getUserId().equals(actionUserId);
    }
}
