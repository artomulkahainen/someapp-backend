/*package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.repositories.*;
import com.someapp.backend.services.UserDetailsServiceImpl;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.TestData;
import com.someapp.backend.util.requests.LoginRequest;
import com.someapp.backend.util.requests.ModifyRelationshipRequest;
import com.someapp.backend.util.requests.NewRelationshipRequest;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class RelationshipControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    private TestData testData;

    @Before
    public void initializeTestData() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        testData = new TestData();
        testData.createTestData(userRepository,
                postRepository, postCommentRepository,
                postLikeRepository, relationshipRepository);
    }

    // testData.getUserId2()
    // id: 3ee5ba25-4221-485a-8734-3b91c7144275
    // token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzZWU1YmEyNS00MjIxLTQ4NWEtODczNC0zYjkxYzcxNDQyNzU7eXliZXJpIiwiZXhwIjo2MjE5OTY5ODQwMCwiaWF0IjoxNjI1NzI5NDIzfQ.uxecJUisoQ5dQKx8hcIB_yh05YDi_60c9H3GUlh5XqxGu8UCTLj-2oDzG7tEScowyyAI2AdoKxBABhT1dXg1zg
    @Test
    @WithMockUser(username = "yyberi")
    public void createNewRelationship() throws Exception {
        mockMvc.perform(post("/loginByUsingPOST")
                .content(Format.asJsonString(new LoginRequest("yyberi", "korkki")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzZWU1YmEyNS00MjIxLTQ4NWEtODczNC0zYjkxYzcxNDQyNzU7eXliZXJpIiwiZXhwIjo2MjE5OTY5ODQwMCwiaWF0IjoxNjI1NzI5NDIzfQ.uxecJUisoQ5dQKx8hcIB_yh05YDi_60c9H3GUlh5XqxGu8UCTLj-2oDzG7tEScowyyAI2AdoKxBABhT1dXg1zg");
        mockMvc.perform(post("/saveNewRelationshipByUsingPOST")
                //.headers(headers)
                .content(Format.asJsonString(
                        new NewRelationshipRequest(
                                testData.getUserId3())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.user1").isNotEmpty())
                .andExpect(jsonPath("$.user2").isNotEmpty());
    }
    /*
    @Test
    public void notPossibleToCreateSameRelationshipAgain() throws Exception {
        mockMvc.perform(post("/saveNewRelationshipByUsingPOST")
                .content(Format.asJsonString(new NewRelationshipRequest(
                        testData.getUserId(),
                        testData.getUserId2(),
                        testData.getUserId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Relationship is already created."));
    }*/

    /*@Test
    public void acceptRelationshipInvite() throws Exception {
        mockMvc.perform(put("/updateRelationshipByUsingPUT")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId2(),
                        1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value(1));
    }

    @Test
    public void declineRelationshipInvite() throws Exception {
        mockMvc.perform(put("/updateRelationshipByUsingPUT")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId2(),
                        2)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value(2));
    }

    @Test
    public void relationshipActionUserCantModifyRelationshipStatus() throws Exception {
        mockMvc.perform(put("/updateRelationshipByUsingPUT")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId(),
                        1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Modifying user has no permits to modify the relationship."));
    }

    @Test
    public void relationshipCantBeModifiedToPendingState() throws Exception {
        mockMvc.perform(put("/updateRelationshipByUsingPUT")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId2(),
                        0)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    public void relationshipCantBeModifiedToStatusAbove3() throws Exception {
        mockMvc.perform(put("/updateRelationshipByUsingPUT")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId2(),
                        4)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

}*/
