package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.*;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.TestData;
import com.someapp.backend.util.requests.LikePostRequest;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostLikeControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    MockMvc mockMvc;

    private TestData testData;

    @Before
    public void initializeTestData() throws Exception {
        testData = new TestData();
        testData.createTestData(userRepository,
                postRepository, postCommentRepository,
                postLikeRepository, relationshipRepository);
    }

    @Test
    public void findLikesFromOnePost() throws Exception {
        MvcResult res = mockMvc.perform(get("/posts/likes/{postId}", testData.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(PostLike.class)))).andReturn();

        assertEquals(true, res.getResponse().getContentAsString().contains(testData.getUserId2().toString()));
    }

    @Test
    public void returnEmptyArrayWhenNoLikesAtAll() throws Exception {
        mockMvc.perform(get("/posts/likes/{postId}", testData.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(emptyCollectionOf(PostLike.class))));
    }

    @Test
    public void likingExistingPostIsPossible() throws Exception {
        User newUser = new User("Kommando", "pahapoika");
        userRepository.save(newUser);

        MvcResult res = mockMvc.perform(post("/posts/likes")
                .content(Format.asJsonString(
                        new LikePostRequest(newUser.getId(), testData.getPostId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.userId").isNotEmpty()).andReturn();

        assertEquals(true, res.getResponse().getContentAsString().contains(newUser.getId().toString()));
        assertEquals(true, res.getResponse().getContentAsString().contains(testData.getPostId().toString()));
    }

    @Test
    public void likingUnexistingPostIsNotPossible() throws Exception {
        mockMvc.perform(post("/posts/likes")
                .content(Format.asJsonString(
                        new LikePostRequest(testData.getUserId(),
                                UUID.fromString("a52fbdae-b373-4398-ab59-acee35e4414a"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void likingPostWithUnexistingUserIsNotPossible() throws Exception {
        mockMvc.perform(post("/posts/likes")
                .content(Format.asJsonString(
                        new LikePostRequest(UUID.fromString("a52fbdae-b373-4398-ab59-acee35e4414a"),
                                testData.getPostId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void unLikingisPossible() throws Exception {
        MvcResult res = mockMvc.perform(delete("/posts/likes/{postLikeId}", testData.getPostLikeId2()))
                .andExpect(status().isOk()).andReturn();

        assertEquals(true, res.getResponse().getContentAsString().contains(testData.getPostLikeId2().toString()));
        assertEquals(true, userRepository.findById(testData.getUserId()).isPresent());
        assertEquals(true, postRepository.findById(testData.getPostId()).isPresent());
    }

}
