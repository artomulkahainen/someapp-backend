package com.someapp.backend.services;

import com.someapp.backend.entities.PostComment;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl {

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public PostComment save(SendPostCommentRequest sendPostCommentRequest) {
        return postCommentRepository.save(new PostComment(sendPostCommentRequest.getPostComment(),
                postRepository.getById(sendPostCommentRequest.getPostId()),
                userRepository.getById(sendPostCommentRequest.getUserId())));
    }
}
