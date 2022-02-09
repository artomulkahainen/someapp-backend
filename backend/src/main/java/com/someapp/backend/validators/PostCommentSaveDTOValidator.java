package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.services.PostService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostCommentSaveDTOValidator implements Validator {

    private final RelationshipService relationshipService;
    private final PostService postService;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public PostCommentSaveDTOValidator(RelationshipService relationshipService,
                                       PostService postService,
                                       JWTTokenUtil jwtTokenUtil) {
        this.relationshipService = relationshipService;
        this.postService = postService;
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
        final Optional<Post> post = postService.findPostById(postCommentSaveDTO.getPostId());
        final UUID postUserId = post.orElse(null).getUserId();

        // POST CREATOR AND POST COMMENTER MUST HAVE ACTIVE RELATIONSHIP, UNLESS COMMENTING OWN POST EXCEPTION HAVE TO BE ADDED
        if (post.isPresent() && !actionUserId.equals(postUserId)) {
            isActiveRelationship(actionUserId, postUserId, errors);
        }
    }

    private void isActiveRelationship(UUID actionUserId, UUID postCreatorId, Errors errors) {
        if (!relationshipService.usersHaveActiveRelationship(actionUserId, postCreatorId)) {
            errors.reject("Active relationship with post creator is needed to write a comment");
        }
    }
}


