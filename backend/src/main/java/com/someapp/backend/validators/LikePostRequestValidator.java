package com.someapp.backend.validators;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class LikePostRequestValidator implements Validator {

    private final PostLikeService postLikeService;
    private final PostRepository postRepository;
    private final RelationshipService relationshipService;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest req;

    public LikePostRequestValidator(PostLikeService postLikeService,
                                    PostRepository postRepository,
                                    RelationshipService relationshipService,
                                    JWTTokenUtil jwtTokenUtil) {
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
        LikePostRequest likePostRequest = (LikePostRequest) target;
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        /**
         * REJECT, IF ACTION USER AND POST CREATOR ARE NOT FRIENDS
         * UNLESS LIKING OWN POST
         */

        if (!isOwnPost(likePostRequest.getPostId(), actionUserId)
                && /*!relationshipService.usersHaveActiveRelationship(actionUserId, likePostRequest.getPostUserId())*/true) {
            errors.reject("Action user and post creator user doesn't have active relationship");
        }

        /**
         * IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, REJECT
         */

        if (postLikeService.likeAlreadyExists(actionUserId, likePostRequest)) {
            errors.reject("Post is already liked by the action user");
        }
    }

    private boolean isOwnPost(UUID postId, UUID actionUserId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.isPresent() && post.get().getUserId().equals(actionUserId);
    }
}
