package com.someapp.backend.IT;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.utils.requests.LoginRequest;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                                UUID.fromString("30d868a1-e7c9-48da-881f-c6348598b0fd"),
                                UUID.fromString("323fe607-9bdc-42fe-92cd-e7ab9cf08cac"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UUID newPostLike = UUID.fromString(new JSONObject(response.getContentAsString()).getString("uuid"));
        assertTrue(repository.findById(newPostLike).isPresent());
    }

    /*@Test
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
    }*/

}
