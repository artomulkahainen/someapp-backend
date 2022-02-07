package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {

    @Autowired
    PostCommentMapper postCommentMapper;

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
}
