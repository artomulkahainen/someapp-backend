package com.someapp.backend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.someapp.backend.entities.Post;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.TestData;
import com.someapp.backend.util.requests.SendPostRequest;
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
import java.util.stream.Collectors;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private MockMvc mockMvc;

    private TestData testData;

    @Before
    public void initializeTestData() throws Exception {
        testData = new TestData();
        testData.createPostTestData(postRepository, userRepository);
    }

    /*@Test
    public void findPostsIsSuccessful() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(Post.class))));
    }*/

    @Test
    public void sendNewPostSuccessfully() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(new SendPostRequest("Let's have a tea.", testData.getUserId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void postCantBeSentWithoutExistingUserId() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(
                        new SendPostRequest("Let's have a t", UUID.fromString("87156b1f-fb34-43ec-8e45-82e82e67fa3b"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("No user found with given uuid"));
    }


    @Test
    public void tooShortPostsCantBeSent() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(new SendPostRequest("", testData.getUserId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").isNotEmpty());
    }

    @Test
    public void tooLongPostsCantBeSent() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(new SendPostRequest("writingVeryLong\n" +
                        "MessagewritingVery\n" +
                        "LongMessagewritingVe\n" +
                        "ryLongMessagewritingVery\n" +
                        "LongMessawritingVeryLong\n" +
                        "MessagewritingVery\n" +
                        "LongMessagewritingVe\n" +
                        "ryLongMessagewritingVery\n" +
                        "LongMessawritingVeryLong\n" +
                        "MessagewritingVery\n" +
                        "LongMessagewritingVe\n" +
                        "ryLongMessageee", testData.getUserId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post length must be between 1-250 letters."));
    }

    @Test
    public void deletePostIsSuccessful() throws Exception {
        Post newPost = postRepository.save(new Post("Heipodei", userRepository.getById(testData.getUserId())));

        mockMvc.perform(delete("/posts/{uuid}", newPost.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uuid").isNotEmpty());

        assertEquals(true, userRepository.findById(testData.getUserId()).isPresent());
        assertEquals(true, postLikeRepository
                .findAll()
                .stream()
                .filter(postLike -> postLike.getPostId().equals(newPost.getId()))
                .collect(Collectors.toList())
                .isEmpty());
        assertEquals(true, postCommentRepository
                .findAll()
                .stream()
                .filter(postComment -> postComment.getPostId().equals(newPost.getId()))
                .collect(Collectors.toList())
                .isEmpty());
    }


}
