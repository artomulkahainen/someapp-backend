package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PostMapper {

    private final ExtendedUserDetailsService userDetailsService;
    private final PostCommentMapper postCommentMapper;

    public PostMapper(final ExtendedUserDetailsService userDetailsService,
                      final PostCommentMapper postCommentMapper) {
        this.userDetailsService = userDetailsService;
        this.postCommentMapper = postCommentMapper;
    }

    public List<PostDTO> mapPostsToPostDTOs(List<Post> posts) {
        return posts
                .stream()
                .map(this::mapPostToPostDTO)
                .collect(ImmutableList.toImmutableList());
    }

    public PostDTO mapPostToPostDTO(Post post) {
        return new PostDTO(
                post.getUUID(),
                post.getCreatedDate(),
                post.getPost(),
                post.getUserId(),
                post.getPostComments()
                        .stream()
                        .map(postCommentMapper::mapPostCommentToPostCommentDTO)
                        .collect(ImmutableList.toImmutableList()),
                post.getPostLikes()
                        .stream()
                        .map(like -> like.getUserId())
                        .collect(ImmutableList.toImmutableList())
        );
    }

    public Post mapSendPostRequestToPost(UUID actionUserId, SendPostRequest sendPostRequest) {
        final User user = userDetailsService.findUserById(actionUserId)
                .orElseThrow(ResourceNotFoundException::new);
        return new Post(sendPostRequest.getPost(), user);
    }
}
