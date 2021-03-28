package com.someapp.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.TestData;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostCommentControllerTest {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private TestData testData;

    @Before
    public void initializeTestData() throws Exception {
        testData = new TestData();
        testData.createPostCommentTestData(postCommentRepository, postRepository, userRepository);
    }

    @Test
    public void findCommentsFromOnePostSuccessfully() throws Exception {
        mockMvc.perform(get("/posts/comments/{postId}", testData.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(PostComment.class))));
    }

    @Test
    public void postWithNoCommentsReturnsEmptyArray() throws Exception {
        Post newPost = postRepository.save(
                new Post("no comments here pls", userRepository.getById(testData.getUserId())));

        mockMvc.perform(get("/posts/comments/{postId}", newPost.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void queryWithUnexistingPostReturnsError() throws Exception {
        mockMvc.perform(get("/posts/comments/{postId}",
                UUID.fromString("329dd70c-a441-4977-a242-6554a1d1f5ba")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post was not found with given uuid"));
    }

    @Test
    public void sendNewPostCommentSuccessfully() throws Exception {
        User newUser = new User("Jouko", "jokkeboi");
        userRepository.save(newUser);

        mockMvc.perform(post("/posts/comments")
                .content(Format.asJsonString(
                        new SendPostCommentRequest("So nice post!",
                                testData.getPostId(), newUser.getId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.postComment").value("So nice post!"))
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    public void newPostCommentCantBeSentWithoutExistingPostId() throws Exception {
        User newUser = new User("koljatti", "koliattipojke");
        userRepository.save(newUser);

        mockMvc.perform(post("/posts/comments")
                .content(Format.asJsonString(
                        new SendPostCommentRequest("So nice post!",
                                UUID.fromString("329dd70c-a441-4977-a242-6554a1d1f5ba"), newUser.getId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post or user was not found with given uuid"));
    }

    @Test
    public void newPostCommentCantBeSentWithoutExistingUserId() throws Exception {
        mockMvc.perform(post("/posts/comments")
                .content(Format.asJsonString(
                        new SendPostCommentRequest("So nice post!",
                                testData.getPostId(),
                                UUID.fromString("329dd70c-a441-4977-a242-6554a1d1f5ba"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post or user was not found with given uuid"));
    }

    /*@Test
    public void postCommentCanBeDeleted() throws Exception {
        PostComment newPostComment = new PostComment("gj!",
                postRepository.getById(testData.getPostId()),
                userRepository.getById(testData.getUserId()));
        postCommentRepository.save(newPostComment);

        mockMvc.perform(delete("/posts/comments/{postCommentId}", testData.getPostComment2Id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").isNotEmpty());
    }*/

}

