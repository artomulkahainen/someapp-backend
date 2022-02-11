package com.someapp.backend.services;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
import com.someapp.backend.mappers.PostLikeMapper;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostLikeMapper postLikeMapper;

    @Override
    public PostLike save(LikePostRequest likePostRequest) {
        return postLikeRepository.save(postLikeMapper.mapLikePostRequestToPostLike(likePostRequest));
    }

    @Override
    public DeleteResponse delete(UnlikePostRequest unlikePostRequest) {
        postLikeRepository.findById(unlikePostRequest.getUuid())
                .orElseThrow(ResourceNotFoundException::new);
        postLikeRepository.deleteById(unlikePostRequest.getUuid());
        return new DeleteResponse(unlikePostRequest.getUuid(), "Successfully unliked");
    }

    @Override
    public Optional<PostLike> findPostLikeById(UUID uuid) {
        return postLikeRepository.findById(uuid);
    }

    @Override
    public boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return postLikeRepository
                .findAll()
                .stream()
                .anyMatch(like -> likeMatches(actionUserId, likePostRequest.getPostId(), like));
    }

    private boolean likeMatches(UUID actionUserId, UUID postId, PostLike like) {
        return Objects.equals(postId, like.getPostId()) && Objects.equals(actionUserId, like.getUserId());
    }
}
