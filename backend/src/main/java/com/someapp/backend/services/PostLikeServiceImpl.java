package com.someapp.backend.services;

import static com.someapp.backend.testUtility.jwt.JWTTokenUtil.getIdFromToken;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.testUtility.customExceptions.BadArgumentException;
import com.someapp.backend.testUtility.customExceptions.ResourceNotFoundException;
import com.someapp.backend.testUtility.jwt.JWTTokenUtil;
import com.someapp.backend.testUtility.requests.LikePostRequest;
import com.someapp.backend.testUtility.requests.UnlikePostRequest;
import com.someapp.backend.testUtility.responses.DeleteResponse;
import com.someapp.backend.validators.RelationshipValidator;
import com.someapp.backend.validators.UserPostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    RelationshipValidator relationshipValidator;

    @Autowired
    UserPostValidator userPostValidator;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    private boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return !postLikeRepository
            .findByUserUUIDAndPostUUID(actionUserId, likePostRequest.getPostId()).isPresent();
    }

    @Override
    public PostLike save(HttpServletRequest req, LikePostRequest likePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        // IF ACTION USER AND USER WHO MADE THE POST ARE NOT FRIENDS, THROW AN EXCEPTION
        if (!relationshipValidator.isActiveRelationship(actionUserId, likePostRequest.getPostUserId())) {
            throw new BadArgumentException("Relationship with given users is not active");

            // IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, THROW AN EXCEPTION
        } else if (!likeAlreadyExists(actionUserId, likePostRequest)) {
            throw new BadArgumentException("Post like is already found with given uuids");

            // CHECK IF POST AND USER IS FOUND FROM DB
        } else if (!userPostValidator.isValid(likePostRequest.getPostId(), actionUserId)) {
            throw new ResourceNotFoundException("Either post or user was not found");

            // IF ABOVE CHECKS DON'T MATCH, LIKE THE POST
        } else {
            return postLikeRepository.save(
                    new PostLike(postRepository.getById(likePostRequest.getPostId()),
                            userRepository.getById(actionUserId)));
        }

    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, UnlikePostRequest unlikePostRequest) {
        UUID actionUserId = getIdFromToken(req);
        Optional<PostLike> likeToDelete = getLikeById(unlikePostRequest.getPostLikeId());

        // IF LIKE IS NOT FOUND, THROW AN EXCEPTION
        if (likeToDelete == null) {
            throw new ResourceNotFoundException("Post like was not found");

            // VALIDATE THAT UNLIKING USER IS LIKE'S OWNER
        } else if (!likeToDelete.get().getUserId().equals(actionUserId)) {
            throw new BadArgumentException("Unliking is only possible from the like owner");

            // IF ABOVE CHECKS ARE NOT TRUE, UNLIKE THE POST
        } else {
            postLikeRepository.deleteById(unlikePostRequest.getPostLikeId());
            return new DeleteResponse(unlikePostRequest.getPostLikeId(), "Successfully unliked");
        }
    }

    private Optional<PostLike> getLikeById(UUID id) {
        return postLikeRepository.findById(id);
    }
}
