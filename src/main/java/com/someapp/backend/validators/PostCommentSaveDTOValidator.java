package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.services.PostService;
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
public class PostCommentSaveDTOValidator implements Validator {

    private final RelationshipService relationshipService;
    private final PostService postService;
    private final JWTTokenUtil jwtTokenUtil;

    public PostCommentSaveDTOValidator(
            final RelationshipService relationshipService,
            final PostService postService,
            final JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.postService = postService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(final Class<?> clazz) {

        return PostCommentSaveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        final PostCommentSaveDTO postCommentSaveDTO =
                (PostCommentSaveDTO) target;
        final Optional<Post> post =
                postService.findPostById(postCommentSaveDTO.getPostId());
        final UUID postUserId =
                post.orElse(null).getUserId();
        final Optional<Relationship> relationshipBetweenUsers = relationshipService.findUsersRelationships(actionUserId)
                .stream().filter(relationship -> relationship.getRelationshipWith().equals(postUserId))
                .findAny();

        if (!actionUserId.equals(postUserId)) {
            if (relationshipBetweenUsers.isEmpty()) {
                errors.reject("No relationship between users");
                return;
            }

            // POST CREATOR AND POST COMMENTER MUST HAVE ACTIVE RELATIONSHIP,
            // UNLESS COMMENTING OWN POST EXCEPTION HAVE TO BE ADDED
            isActiveRelationship(relationshipBetweenUsers.get().getUniqueId(), errors);
        }
    }

    private void isActiveRelationship(String relationshipId, Errors errors) {
        if (!relationshipService.usersHaveActiveRelationship(relationshipId)) {
            errors.reject("Active relationship " +
                    "with post creator is needed to write a comment");
        }
    }
}


