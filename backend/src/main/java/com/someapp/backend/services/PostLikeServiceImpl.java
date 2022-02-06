package com.someapp.backend.services;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.utils.customExceptions.ResourceNotFoundException;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.utils.requests.UnlikePostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
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
    UserPostValidator userPostValidator;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Override
    public boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return postLikeRepository
                .findByUserUUIDAndPostUUID(actionUserId, likePostRequest.getPostId()).isPresent();
    }

    @Override
    public PostLike save(HttpServletRequest req, LikePostRequest likePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        // CHECK IF POST AND USER IS FOUND FROM DB
        if (!userPostValidator.isValid(likePostRequest.getPostId(), actionUserId)) {
            throw new ResourceNotFoundException("Either post or user was not found");
        }

        return postLikeRepository.save(
                new PostLike(postRepository.getById(likePostRequest.getPostId()),
                        userRepository.getById(actionUserId)));
    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, UnlikePostRequest unlikePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
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

    @Override
    public Optional<PostLike> getLikeById(UUID id) {
        return postLikeRepository.findById(id);
    }
}
