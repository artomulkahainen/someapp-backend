package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.entities.Post;

public class PostMapper {

    public PostDTO mapPostToPostDTO(Post post) {
        return new PostDTO(
                post.getUUID(),
                post.getCreatedDate(),
                post.getPost(),
                post.getUserId(),
                post.getPostComments(),
                post.getPostLikes()
                        .stream()
                        .map(like -> like.getUserId())
                        .collect(ImmutableList.toImmutableList())
        );
    }
}
