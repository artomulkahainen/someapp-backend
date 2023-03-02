package com.someapp.backend.mappers;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostCommentMapper {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCommentMapper(final PostRepository postRepository,
                             final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostCommentDTO mapPostCommentToPostCommentDTO(
            final PostComment postComment) {
        return new PostCommentDTO(
                postComment.getUUID(),
                postComment.getCreatedDate(),
                postComment.getPostComment(),
                postComment.getPostId(),
                postComment.getUserId(),
                postComment.getUsername()
        );
    }

    public PostComment mapPostCommentSaveDTOToPostComment(
            final UUID actionUserId,
            final PostCommentSaveDTO postCommentSaveDTO) {
        final Post post = postRepository.findById(
                postCommentSaveDTO.getPostId())
                .orElseThrow(ResourceNotFoundException::new);
        final User user = userRepository.findById(actionUserId)
                .orElseThrow(ResourceNotFoundException::new);
        return new PostComment(postCommentSaveDTO.getPostComment(),
                post, user);
    }
}
