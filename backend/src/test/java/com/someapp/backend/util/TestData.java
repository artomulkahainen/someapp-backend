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
    private UUID userId3;
    private UUID postId;
    private UUID postCommentId;
    private UUID postComment2Id;
    private UUID postLikeId;
    private UUID postLikeId2;

    public TestData() {};

    public void createUsers(UserRepository userRepository) {
        User user = new User("kalleKustaa", "korkki");
        User user2 = new User("yyberi", "korkki");
        User user3 = new User("Aino", "ainoomaaa");
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        this.userId = user.getId();
        this.userId2 = user2.getId();
        this.userId3 = user3.getId();
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
        PostLike postLike2 = new PostLike(postRepository.getById(postId), userRepository.getById(userId3));
        postLikeRepository.save(postLike);
        postLikeRepository.save(postLike2);
        this.postLikeId = postLike.getId();
        this.postLikeId2 = postLike2.getId();
    }

    public void createPostComments(PostRepository postRepository,
                                  PostCommentRepository postCommentRepository,
                                  UserRepository userRepository) {
        PostComment postComment = new PostComment("Nice post!",
                postRepository.getById(postId),
                userRepository.getById(userId2));
        postCommentRepository.save(postComment);
        PostComment postComment2 = new PostComment("Yea very nice",
                postRepository.getById(postId),
                userRepository.getById(userId));
        postCommentRepository.save(postComment2);
        this.postCommentId = postComment.getId();
        this.postComment2Id = postComment2.getId();
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
            System.out.println("isPostLikes empty");
            System.out.println(postLikeRepository.findAll().isEmpty());
            System.out.println("can post likes get index 0:");
            System.out.println(postLikeRepository.findAll().get(0).getId());
            System.out.println("current postlike id:");
            System.out.println(postLikeId);
            System.out.println("is posts empty:");
            System.out.println(postRepository.findAll().isEmpty());
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
            System.out.println("postlike id after setting:");
            System.out.println(postLikeId);
            if (postLikeRepository.findAll().stream().count() >= 2) {
                this.postLikeId2 = postLikeRepository
                        .findAll()
                        .get(1)
                        .getId();
            }
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
            createPostComments(postRepository, postCommentRepository, userRepository);
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
            this.postComment2Id = postCommentRepository
                    .findAll()
                    .get(1)
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

    public UUID getUserId2() { return userId2; }

    public UUID getPostId() {
        return postId;
    }

    public UUID getPostComment2Id() {
        return postComment2Id;
    }

    public UUID getPostLikeId() { return postLikeId; }

    public UUID getPostLikeId2() { return postLikeId2; }
}
