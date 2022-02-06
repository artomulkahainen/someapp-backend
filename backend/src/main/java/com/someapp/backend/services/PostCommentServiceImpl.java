package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.responses.DeleteResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JWTTokenUtil jwtTokenUtil;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
                                  PostRepository postRepository,
                                  UserRepository userRepository,
                                  JWTTokenUtil jwtTokenUtil) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public PostComment save(HttpServletRequest req, PostCommentSaveDTO postCommentSaveDTO) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        Post post = postRepository.findById(postCommentSaveDTO.getPostId())
                .orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findById(actionUserId).orElseThrow(ResourceNotFoundException::new);

        return postCommentRepository.save(new PostComment(postCommentSaveDTO.getPostComment(), post, user));
    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, UUID postCommentId) {
        PostComment commentToDelete = postCommentRepository.findById(postCommentId)
                .orElseThrow(ResourceNotFoundException::new);

        postCommentRepository.deleteById(commentToDelete.getId());
        return new DeleteResponse(postCommentId, "Successfully deleted post comment");
    }
}
