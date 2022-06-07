package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.PostLikeDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsService;
import com.someapp.backend.services.PostService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class PostLikeMapper {

    private final PostService postService;
    private final ExtendedUserDetailsService userService;
    private final JWTTokenUtil jwtTokenUtil;

    public PostLikeMapper(final PostService postService,
                          final ExtendedUserDetailsService userService,
                          final JWTTokenUtil jwtTokenUtil) {
        this.postService = postService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public List<PostLikeDTO> mapPostLikesToPostLikeDTOs(List<PostLike> postLikes) {
        return postLikes.stream().map(this::mapPostLikeToPostLikeDTO).collect(ImmutableList.toImmutableList());
    }

    public PostLikeDTO mapPostLikeToPostLikeDTO(PostLike postLike) {
        return new PostLikeDTO(postLike.getUUID(), postLike.getCreatedDate(), postLike.getPostId());
    }

    public PostLike mapLikePostRequestToPostLike(LikePostRequest likePostRequest) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final Post post = postService.findPostById(likePostRequest.getPostId())
                .orElseThrow(ResourceNotFoundException::new);
        final User user = userService.findUserById(jwtTokenUtil.getIdFromToken(req))
                .orElseThrow(ResourceNotFoundException::new);
        return new PostLike(post, user);
    }
}
