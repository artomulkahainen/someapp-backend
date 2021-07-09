package com.someapp.backend.services;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.requests.LikePostRequest;
import com.someapp.backend.util.requests.UnlikePostRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostLikeServiceImpl {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    public boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return !postLikeRepository
            .findByUserUUIDAndPostUUID(actionUserId, likePostRequest.getPostId()).isPresent();
    }

    public PostLike save(LikePostRequest likePostRequest, UUID userId) {
        return postLikeRepository.save(
                new PostLike(postRepository.getById(likePostRequest.getPostId()),
                        userRepository.getById(userId)));
    }

    public DeleteResponse delete(UnlikePostRequest unlikePostRequest) {
        postLikeRepository.deleteById(unlikePostRequest.getPostLikeId());
        return new DeleteResponse(unlikePostRequest.getPostLikeId(), "Successfully unliked");
    }

    public Optional<PostLike> getLikeById(UUID id) {
        return postLikeRepository.findById(id);
    }
}
