package com.someapp.backend.controllers;

import com.someapp.backend.entities.Relationship;
import com.someapp.backend.repositories.*;
import com.someapp.backend.util.Format;
import com.someapp.backend.util.TestData;
import com.someapp.backend.util.requests.ModifyRelationshipRequest;
import com.someapp.backend.util.requests.NewRelationshipRequest;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RelationshipControllerTest {

    @Autowired
    MockMvc mockMvc;

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
        testData = new TestData();
        testData.createTestData(userRepository,
                postRepository, postCommentRepository,
                postLikeRepository, relationshipRepository);
    }

    /*@Test
    public void getUserRelationships() throws Exception {
        mockMvc.perform(get("/relationships/{userId}", testData.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(Relationship.class))));
    }

    @Test
    public void returnEmptyArrayWithWrongUserId() throws Exception {
        mockMvc.perform(get("/relationships/{userId}", testData.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }*/

    @Test
    public void createNewRelationship() throws Exception {
        mockMvc.perform(post("/relationships")
                .content(Format.asJsonString(
                        new NewRelationshipRequest(
                                testData.getUserId2(),
                                testData.getUserId3(),
                                testData.getUserId2())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.user1").isNotEmpty())
                .andExpect(jsonPath("$.user2").isNotEmpty());
    }

    @Test
    public void notPossibleToCreateSameRelationshipAgain() throws Exception {
        mockMvc.perform(post("/relationships")
                .content(Format.asJsonString(new NewRelationshipRequest(
                        testData.getUserId(),
                        testData.getUserId2(),
                        testData.getUserId())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Relationship is already created."));
    }

    @Test
    public void acceptRelationshipInvite() throws Exception {
        mockMvc.perform(put("/relationships")
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
        mockMvc.perform(put("/relationships")
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
        mockMvc.perform(put("/relationships")
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
        mockMvc.perform(put("/relationships")
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
        mockMvc.perform(put("/relationships")
                .content(Format.asJsonString(new ModifyRelationshipRequest(
                        testData.getRelationshipId(),
                        testData.getUserId2(),
                        4)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

}
