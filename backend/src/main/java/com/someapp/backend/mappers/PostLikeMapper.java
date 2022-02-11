package com.someapp.backend.mappers;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.PostLikeDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.services.PostService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostLikeMapper {

    private PostService postService;
    private ExtendedUserDetailsService userService;
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public PostLikeMapper(PostService postService, ExtendedUserDetailsService userService, JWTTokenUtil jwtTokenUtil) {
        this.postService = postService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public PostLikeDTO mapPostLikeToPostLikeDTO(PostLike postLike) {
        return new PostLikeDTO(postLike.getUUID(), postLike.getCreatedDate(), postLike.getPostId());
    }

    public PostLike mapLikePostRequestToPostLike(LikePostRequest likePostRequest) {
        Post post = postService.findPostById(likePostRequest.getPostId()).orElseThrow(ResourceNotFoundException::new);
        User user = userService.findUserById(jwtTokenUtil.getIdFromToken(req))
                .orElseThrow(ResourceNotFoundException::new);
        return new PostLike(post, user);
    }
}
