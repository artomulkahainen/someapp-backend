package com.someapp.backend.util;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;

public class TestData {

    private UUID userId;
    private UUID postId;
    private UUID postCommentId;

    public TestData() {};

    public void createPostCommentTestData(PostCommentRepository postCommentRepository,
                       PostRepository postRepository,
                       UserRepository userRepository) throws Exception {
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()
                && postCommentRepository.findAll().isEmpty()) {
            User user = new User("kalleKustaa", "korkki");
            User user2 = new User("yyberi", "korkki");
            Post post = new Post("Oh yeah", user);
            PostComment postComment = new PostComment("Nice post!", post, user2);
            userRepository.save(user);
            postRepository.save(post);
            postCommentRepository.save(postComment);
            this.userId = user.getId();
            this.postId = post.getId();
            this.postCommentId = postComment.getId();
        } else {
            this.userId = userRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
            this.postId = postRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
            this.postCommentId = postCommentRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
        }
    }

    public void createPostTestData(PostRepository postRepository, UserRepository userRepository) {
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()) {
            User user = new User("kalleKustaa", "korkki");
            Post post = new Post("Oh yeah", user);
            userRepository.save(user);
            postRepository.save(post);
            this.userId = user.getId();
            this.postId = post.getId();
        } else {
            this.userId = userRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
            this.postId = postRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getPostCommentId() {
        return postCommentId;
    }
}
