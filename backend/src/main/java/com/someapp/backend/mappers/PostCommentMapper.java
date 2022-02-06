package com.someapp.backend.mappers;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.entities.PostComment;
import org.springframework.stereotype.Component;

@Component
public class PostCommentMapper {

    public PostCommentDTO mapPostCommentToPostCommentDTO(PostComment postComment) {
        return new PostCommentDTO(
                postComment.getUUID(),
                postComment.getCreatedDate(),
                postComment.getPostComment(),
                postComment.getPostId(),
                postComment.getUserId()
        );
    }
}
