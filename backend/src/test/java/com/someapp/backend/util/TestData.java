package com.someapp.backend.util;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;

import java.util.UUID;

public class TestData {

    private UUID userId;
    private UUID userId2;
    private UUID postId;
    private UUID postCommentId;
    private UUID postLikeId;

    public TestData() {};

    public void createUsers(UserRepository userRepository) {
        User user = new User("kalleKustaa", "korkki");
        User user2 = new User("yyberi", "korkki");
        userRepository.save(user);
        userRepository.save(user2);
        this.userId = user.getId();
        this.userId2 = user.getId();
    }

    public void createPost(PostRepository postRepository, UserRepository userRepository) {
        Post post = new Post("Oh yeah", userRepository.getById(userId));
        postRepository.save(post);
        this.postId = post.getId();
    }

    public void createPostLike(PostRepository postRepository,
                               UserRepository userRepository,
                               PostLikeRepository postLikeRepository) {
        PostLike postLike = new PostLike(postRepository.getById(postId), userRepository.getById(userId2));
        postLikeRepository.save(postLike);
        this.postLikeId = postLike.getId();
    }

    public void createPostComment(PostRepository postRepository,
                                  PostCommentRepository postCommentRepository,
                                  UserRepository userRepository) {
        PostComment postComment = new PostComment("Nice post!",
                postRepository.getById(postId),
                userRepository.getById(userId2));
        postCommentRepository.save(postComment);
        this.postCommentId = postComment.getId();
    }

    public void createPostLikeTestData(PostLikeRepository postLikeRepository,
                                       PostRepository postRepository,
                                       UserRepository userRepository) throws Exception {
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()
                && postLikeRepository.findAll().isEmpty()) {
            createUsers(userRepository);
            createPost(postRepository, userRepository);
            createPostLike(postRepository, userRepository, postLikeRepository);
        } else {
            this.userId = userRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.userId2 = userRepository
                    .findAll()
                    .get(1)
                    .getId();
            this.postId = postRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.postLikeId = postLikeRepository
                    .findAll()
                    .get(0)
                    .getId();
        }
    }

    public void createPostCommentTestData(PostCommentRepository postCommentRepository,
                       PostRepository postRepository,
                       UserRepository userRepository) throws Exception {
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()
                && postCommentRepository.findAll().isEmpty()) {
            createUsers(userRepository);
            createPost(postRepository, userRepository);
            createPostComment(postRepository, postCommentRepository, userRepository);
        } else {
            this.userId = userRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.userId2 = userRepository
                    .findAll()
                    .get(1)
                    .getId();
            this.postId = postRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.postCommentId = postCommentRepository
                    .findAll()
                    .get(0)
                    .getId();
        }
    }

    public void createPostTestData(PostRepository postRepository, UserRepository userRepository) {
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()) {
            createUsers(userRepository);
            createPost(postRepository, userRepository);
        } else {
            this.userId = userRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.postId = postRepository
                    .findAll()
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
