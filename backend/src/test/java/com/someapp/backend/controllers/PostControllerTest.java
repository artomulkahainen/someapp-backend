package com.someapp.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.requests.SendPostRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    private MockMvc mockMvc;

    private UUID userId;

    @Before
    public void createUserAndPost() throws Exception {
        User user;
        if (userRepository.findAll().isEmpty()
                && postRepository.findAll().isEmpty()) {
            user = new User("kalleKustaa", "korkki");
            userRepository.save(user);
            postRepository.save(new Post("Oh yeah", user));
            this.userId = user.getId();
        } else {
            this.userId = userRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getId();
        }
    }

    @Test
    public void findPostsIsSuccessful() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(jsonPath("$", not(emptyCollectionOf(Post.class))))
                .andExpect(status().isOk());
    }

    @Test
    public void sendNewPostSuccessfully() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(new SendPostRequest("Let's have a tea.", userId.toString())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.post").value("Let's have a tea."));
    }

    @Test
    public void postCantBeSentWithoutExistingUserId() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(
                        new SendPostRequest("Let's have a t", "87156b1f-fb34-43ec-8e45-82e82e67fa3b")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("No user found with given uuid"));
    }

    @Test
    public void tooShortPostsCantBeSent() throws Exception {
        mockMvc.perform(post("/posts")
                .content(Format.asJsonString(new SendPostRequest("", userId.toString())))
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
                        "ryLongMessageee", userId.toString())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post length must be between 1-250 letters."));
    }
}
