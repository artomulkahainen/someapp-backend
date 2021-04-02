package com.someapp.backend.controllers;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    MockMvc mockMvc;

    private TestData testData;

    @Before
    public void initializeTestData() throws Exception {
        testData = new TestData();
        testData.createPostLikeTestData(postLikeRepository, postRepository, userRepository);
    }

    @Test
    public void findLikesFromOnePostSuccessfully() throws Exception {
        mockMvc.perform(get("/posts/likes/{postId}", testData.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(PostLike.class))));

    }

    /*@Test
    public void likingUserMatches() throws Exception {
        mockMvc.perform(get("/posts/likes/{postId}", testData.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userRepository.getById(testData.get)))
    }*/

}
