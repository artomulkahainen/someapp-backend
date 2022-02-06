package com.someapp.backend.services;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostLikeRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.UnlikePostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    JWTTokenUtil jwtTokenUtil;

    @Override
    public boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest) {
        return postLikeRepository
                .findByUserUUIDAndPostUUID(actionUserId, likePostRequest.getPostId()).isPresent();
    }

    @Override
    public PostLike save(HttpServletRequest req, LikePostRequest likePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        Post post = postRepository.findById(likePostRequest.getPostId()).orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findById(actionUserId).orElseThrow(ResourceNotFoundException::new);

        return postLikeRepository.save(new PostLike(post, user));
    }

    @Override
    public DeleteResponse delete(UnlikePostRequest unlikePostRequest) {
        postLikeRepository.findById(unlikePostRequest.getPostLikeId())
                .orElseThrow(ResourceNotFoundException::new);
        postLikeRepository.deleteById(unlikePostRequest.getPostLikeId());
        return new DeleteResponse(unlikePostRequest.getPostLikeId(), "Successfully unliked");
    }
}
