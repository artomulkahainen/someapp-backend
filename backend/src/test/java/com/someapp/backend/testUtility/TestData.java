package com.someapp.backend.testUtility;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class TestData {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    private UUID userId;
    private UUID userId2;
    private UUID userId3;
    private UUID userId4;
    private UUID postId;
    private UUID postCommentId;
    private UUID postComment2Id;
    private UUID postLikeId;
    private UUID postLikeId2;
    private UUID relationshipId;
    private UUID relationshipId2;

    public TestData(UserRepository userRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUsers() {
        User user = new User("kalleKustaa", bCryptPasswordEncoder.encode("korkki"));
        User user2 = new User("yyberi", bCryptPasswordEncoder.encode("korkki"));
        User user3 = new User("Aino", bCryptPasswordEncoder.encode("ainoomaaa"));
        User user4 = new User("jepulis", bCryptPasswordEncoder.encode("jepsjepsjeps"));
        this.userRepository.save(user);
        this.userRepository.save(user2);
        this.userRepository.save(user3);
        this.userRepository.save(user4);

        this.userId = user.getId();
        this.userId2 = user2.getId();
        this.userId3 = user3.getId();
        this.userId4 = user4.getId();
    }

    /*public void createPost(PostRepository postRepository, UserRepository userRepository) {
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

    public void createRelationships(UserRepository userRepository,
                                    RelationshipRepository relationshipRepository) {
        Relationship relationship = relationshipRepository.save(new Relationship(userRepository.getById(userId),
                userRepository.getById(userId2), userId, 0));
        Relationship relationship2 = relationshipRepository.save(new Relationship(userRepository.getById(userId),
                userRepository.getById(userId3), userId, 0));
        this.relationshipId = relationship.getId();
        this.relationshipId2 = relationship2.getId();
    }

    public void createTestData(UserRepository userRepository,
                               PostRepository postRepository,
                               PostCommentRepository postCommentRepository,
                               PostLikeRepository postLikeRepository,
                               RelationshipRepository relationshipRepository) throws Exception {

        createUsers(userRepository);
        createRelationships(userRepository, relationshipRepository);
        createPost(postRepository, userRepository);
        createPostLike(postRepository, userRepository, postLikeRepository);
        createPostComments(postRepository, postCommentRepository, userRepository);

        // If repositories are empty, create totally new data for every repo
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()
                && postLikeRepository.findAll().isEmpty()
                && postCommentRepository.findAll().isEmpty()
                && relationshipRepository.findAll().isEmpty()) {
            createUsers(userRepository);
            createRelationships(userRepository, relationshipRepository);
            createPost(postRepository, userRepository);
            createPostLike(postRepository, userRepository, postLikeRepository);
            createPostComments(postRepository, postCommentRepository, userRepository);

            // If repositories are already filled, init ids
        } else {
            this.userId = userRepository
                    .findAll()
                    .get(0)
                    .getId();
            this.userId2 = userRepository
                    .findAll()
                    .get(1)
                    .getId();
            this.relationshipId = relationshipRepository.findAll().get(0).getId();
            this.relationshipId2 = relationshipRepository.findAll().get(1).getId();
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
            this.postLikeId = postLikeRepository
                    .findAll()
                    .get(0)
                    .getId();
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
    }*/

    public UUID getUserId() {
        return userId;
    }

    public UUID getUserId2() { return userId2; }

    public UUID getUserId3() { return userId3; }

    public UUID getUserId4() { return userId4; }

    /*public UUID getPostId() {
        return postId;
    }

    public UUID getPostComment2Id() {
        return postComment2Id;
    }

    public UUID getPostLikeId() { return postLikeId; }

    public UUID getPostLikeId2() { return postLikeId2; }

    public UUID getRelationshipId() { return relationshipId; }

    public UUID getRelationshipId2() { return relationshipId2; }*/
}
