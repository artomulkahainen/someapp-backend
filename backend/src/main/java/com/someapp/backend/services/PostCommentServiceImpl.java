package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
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
    public PostComment save(HttpServletRequest req, SendPostCommentRequest sendPostCommentRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Post post = postRepository.findById(sendPostCommentRequest.getPostId())
                .orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findById(actionUserId).orElseThrow(ResourceNotFoundException::new);

        return postCommentRepository.save(new PostComment(sendPostCommentRequest.getPostComment(), post, user));
    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, UUIDRequest postCommentId) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        PostComment commentToDelete = postCommentRepository.findById(postCommentId.getUuid())
                .orElseThrow(ResourceNotFoundException::new);

        postCommentRepository.deleteById(commentToDelete.getId());
        return new DeleteResponse(postCommentId.getUuid(), "Successfully deleted post comment");
    }
}
