package com.someapp.backend.services;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.mappers.PostLikeMapper;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostService postService;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    PostLikeMapper postLikeMapper;

    @Override
    public PostLike save(final LikePostRequest likePostRequest) {
        return postLikeRepository.save(postLikeMapper
                .mapLikePostRequestToPostLike(likePostRequest));
    }

    @Override
    public DeleteResponse delete(final UnlikePostRequest unlikePostRequest) throws Exception {
        final Post postToUnlike = postService.findPostById(unlikePostRequest.getUuid())
                .orElseThrow(() -> new Exception("Post not found with provided id"));
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        PostLike postLike = postToUnlike.getPostLikes().stream()
                .filter(like -> like.getUserId().equals(jwtTokenUtil.getIdFromToken(req)))
                .findAny()
                .orElseThrow(() -> new Exception("Post like not found"));

        postLikeRepository.deleteById(postLike.getUUID());
        return new DeleteResponse(unlikePostRequest.getUuid(), jwtTokenUtil.getIdFromToken(req),
                "Successfully unliked");
    }

    @Override
    public Optional<PostLike> findPostLikeById(final UUID uuid) {
        return postLikeRepository.findById(uuid);
    }

    @Override
    public boolean likeAlreadyExists(
            final UUID actionUserId,
            final LikePostRequest likePostRequest) {
        return postLikeRepository
                .findAll()
                .stream()
                .anyMatch(like -> likeMatches(
                        actionUserId, likePostRequest.getPostId(), like));
    }

    private boolean likeMatches(
            final UUID actionUserId,
            final UUID postId,
            final PostLike like) {
        return Objects.equals(postId, like.getPostId())
                && Objects.equals(actionUserId, like.getUserId());
    }
}
