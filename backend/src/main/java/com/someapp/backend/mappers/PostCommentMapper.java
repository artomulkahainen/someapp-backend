package com.someapp.backend.mappers;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostCommentMapper {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostCommentMapper(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostCommentDTO mapPostCommentToPostCommentDTO(PostComment postComment) {
        return new PostCommentDTO(
                postComment.getUUID(),
                postComment.getCreatedDate(),
                postComment.getPostComment(),
                postComment.getPostId(),
                postComment.getUserId()
        );
    }

    public PostComment mapPostCommentSaveDTOToPostComment(UUID actionUserId, PostCommentSaveDTO postCommentSaveDTO) {
        Post post = postRepository.findById(postCommentSaveDTO.getPostId())
                .orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findById(actionUserId).orElseThrow(ResourceNotFoundException::new);
        return new PostComment(postCommentSaveDTO.getPostComment(), post, user);
    }
}
