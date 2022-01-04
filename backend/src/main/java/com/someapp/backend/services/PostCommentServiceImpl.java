package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostCommentServiceImpl {

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

    public PostComment save(HttpServletRequest req, SendPostCommentRequest sendPostCommentRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Optional<Post> post = postRepository.getByUUID(sendPostCommentRequest.getPostId());
        Optional<User> user = userRepository.getByUUID(actionUserId);

        return postCommentRepository.save(new PostComment(sendPostCommentRequest.getPostComment(),
                post.orElseThrow(ResourceNotFoundException::new),
                user.orElseThrow(ResourceNotFoundException::new)));
    }

    public DeleteResponse delete(HttpServletRequest req, UUIDRequest postCommentId) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Optional<PostComment> commentToDelete = postCommentRepository.findById(postCommentId.getUuid());

        // IF COMMENT IS NOT FOUND, THROW AN EXCEPTION
        /*if (!commentToDelete.isPresent()) {
            throw new BadArgumentException();
        }*/

        postCommentRepository.deleteById(postCommentId.getUuid());
        return new DeleteResponse(postCommentId.getUuid(), "Successfully deleted post comment");
    }
}
