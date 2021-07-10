package com.someapp.backend.services;

import com.someapp.backend.entities.PostComment;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import com.someapp.backend.util.validators.RelationshipValidator;
import com.someapp.backend.util.validators.UserPostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostCommentServiceImpl {

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPostValidator userPostValidator;

    @Autowired
    RelationshipValidator relationshipValidator;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    public PostComment save(HttpServletRequest req, SendPostCommentRequest sendPostCommentRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));

        // IF POST OR USER WEREN'T FOUND WITH GIVEN UUID, THROW AN EXCEPTION
        if (userPostValidator.isValid(sendPostCommentRequest.getPostId(), actionUserId)) {
            throw new ResourceNotFoundException("Either post or user was not found");

            // CHECK IF RELATIONSHIP IS VALID
        } else if (!relationshipValidator.isActiveRelationship(actionUserId, sendPostCommentRequest.getPostCreatorId())) {
            throw new BadArgumentException("Active relationship with post creator is needed to write a comment");

            // OTHERWISE SAVE POST COMMENT
        } else {
            return postCommentRepository.save(new PostComment(sendPostCommentRequest.getPostComment(),
                    postRepository.getById(sendPostCommentRequest.getPostId()),
                    userRepository.getById(actionUserId)));
        }
    }

    public DeleteResponse delete(HttpServletRequest req, UUIDRequest postCommentId) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Optional<PostComment> commentToDelete = postCommentRepository.findById(postCommentId.getUuid());

        // IF COMMENT IS NOT FOUND FROM DB, THROW AN EXCEPTION
        if (!commentToDelete.isPresent()) {
            throw new ResourceNotFoundException("Post comment was not found with given uuid");

            // IF DELETING USER != COMMENT CREATED USER
        } else if (!commentToDelete.get().getUserId().equals(actionUserId)) {
            throw new BadArgumentException("Users can delete only their own post comments");

            // OTHERWISE DELETE POST COMMENT
        } else {
            postCommentRepository.deleteById(postCommentId.getUuid());
            return new DeleteResponse(postCommentId.getUuid(), "Successfully deleted post comment");
        }

    }
}
