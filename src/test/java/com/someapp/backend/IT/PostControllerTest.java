package com.someapp.backend.IT;

import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.dto.LoginRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.someapp.backend.testUtility.Format.asJsonString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private PostRepository repository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    private MockMvc mvc;
    private String token;

    @Before
    @Transactional
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // LOGIN FIRST AND STORE THE TOKEN FROM LOGIN
        MockHttpServletResponse response = mvc
                .perform(post("/loginByUsingPOST")
                        .content(asJsonString(new LoginRequest("kalleKustaa", "korkki")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();
        token = new JSONObject(response.getContentAsString()).getString("token");
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/posts.sql"})
    public void creatingNewPostIsPossible() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post("/sendNewPostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new SendPostRequest("Hulabaloo")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.post").value("Hulabaloo"))
                .andExpect(jsonPath("$.userUuid").value("609b08a3-356d-40d8-9a87-b4e1d47abf4d"))
                .andReturn()
                .getResponse();

        UUID newPost = UUID.fromString(new JSONObject(response.getContentAsString()).getString("uuid"));
        assertTrue(repository.findById(newPost).isPresent());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/posts.sql", "/db/postlikes.sql", "/db/postcomments.sql"})
    public void deletingPostIsPossible() throws Exception {
        UUID postId = UUID.fromString("12bffa51-9899-497b-b41a-2b71d8c42629");

        mvc
                .perform(post("/deletePostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new DeletePostRequest(postId)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully deleted post"));

        assertFalse(repository.findById(postId).isPresent());
        assertTrue(postCommentRepository.findAll()
                .stream()
                .filter(p -> p.getPostId().equals(postId))
                .collect(Collectors.toList())
                .isEmpty());
        assertTrue(postLikeRepository.findAll()
                .stream()
                .filter(p -> p.getPostId().equals(postId))
                .collect(Collectors.toList())
                .isEmpty());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/posts.sql"})
    public void cannotDeleteOtherUsersPost() throws Exception {
        UUID postId = UUID.fromString("434811ee-f31f-4929-beec-194f237cf417");

        mvc
                .perform(post("/deletePostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new DeletePostRequest(postId)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Post can be deleted only by post creator."));
    }


/*
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
*/

}
