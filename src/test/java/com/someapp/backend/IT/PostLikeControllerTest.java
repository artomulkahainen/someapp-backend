package com.someapp.backend.IT;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.repositories.PostLikeRepository;
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

import static com.someapp.backend.testUtility.Format.asJsonString;
import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostLikeControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private PostLikeRepository repository;
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
    @Sql(value = {"/db/users.sql", "/db/posts.sql", "/db/postlikes.sql", "/db/relationships.sql"})
    public void likingExistingPostIsPossible() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post("/likePostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new LikePostRequest(
                                UUID.fromString("323fe607-9bdc-42fe-92cd-e7ab9cf08cac"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UUID newPostLike = UUID.fromString(new JSONObject(response.getContentAsString()).getString("uuid"));
        assertTrue(repository.findById(newPostLike).isPresent());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/posts.sql", "/db/postlikes.sql", "/db/relationships.sql"})
    public void unlikingIsPossible() throws Exception {
        assertTrue(repository.findById(UUID.fromString("d2985a64-a139-4eb0-a5fb-f356e0fafb66")).isPresent());
        mvc
                .perform(post("/unlikePostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new UnlikePostRequest(
                                UUID.fromString("434811ee-f31f-4929-beec-194f237cf417"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(repository.findById(UUID.fromString("d2985a64-a139-4eb0-a5fb-f356e0fafb66")).isPresent());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/posts.sql", "/db/postlikes.sql", "/db/relationships.sql"})
    public void likingPostFromUserWithNoActiveRelationship_IsNotPossible() throws Exception {
        mvc
                .perform(post("/likePostByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new LikePostRequest(
                                UUID.fromString("f09823e5-6de1-4042-8ab1-9a273f283ef9"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Action user and post creator user doesn't have active relationship"));
    }

}
