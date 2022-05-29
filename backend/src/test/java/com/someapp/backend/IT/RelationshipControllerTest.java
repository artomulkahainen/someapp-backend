package com.someapp.backend.IT;

import com.someapp.backend.dto.DeclineRelationshipRequest;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.repositories.RelationshipRepository;
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
public class RelationshipControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RelationshipRepository repository;
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
    @Sql("/db/users.sql")
    public void sendNewRelationshipRequestIsSuccessful() throws Exception {
        String actionUserId = "609b08a3-356d-40d8-9a87-b4e1d47abf4d";
        String relationshipWithId = "12bffa51-9899-497b-b41a-2b71d8c42629";

        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new SaveRelationshipDTO(
                                UUID.fromString(relationshipWithId),
                                actionUserId + "," + relationshipWithId,
                                0)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("0"))
                .andExpect(jsonPath("$.uniqueId").value(actionUserId + "," + relationshipWithId))
                .andExpect(jsonPath("$.createdDate").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
    public void cannotCreateNewRelationshipIfExistingIsFound() throws Exception {
        String actionUserId = "508081af-5ba5-4318-b678-983e103a78f3";
        String relationshipWithId = "609b08a3-356d-40d8-9a87-b4e1d47abf4d";

        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new SaveRelationshipDTO(
                                UUID.fromString(actionUserId),
                                actionUserId + "," + relationshipWithId,
                                0)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Existing relationship cannot be changed to pending " +
                                "or new pending relationship cannot be created if existing is found."));

    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
    public void acceptRelationshipRequest() throws Exception {
        String actionUserId = "508081af-5ba5-4318-b678-983e103a78f3";
        String relationshipWithId = "609b08a3-356d-40d8-9a87-b4e1d47abf4d";
        String uniqueId = "508081af-5ba5-4318-b678-983e103a78f3,609b08a3-356d-40d8-9a87-b4e1d47abf4d";

        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new SaveRelationshipDTO(
                                UUID.fromString(actionUserId),
                                actionUserId + "," + relationshipWithId,
                                1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.uniqueId")
                        .value(uniqueId));

        assertTrue(repository.findRelationshipsByUniqueId(uniqueId)
                .stream().anyMatch(relationship -> relationship.getStatus() == 1));
        assertFalse(repository.findRelationshipsByUniqueId(uniqueId)
                .stream().anyMatch(relationship -> relationship.getStatus() == 0));
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
    public void cannotAcceptOtherUserRelationship() throws Exception {
        String actionUserId = "508081af-5ba5-4318-b678-983e103a78f3";
        String relationshipWithId = "12bffa51-9899-497b-b41a-2b71d8c42629";

        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new SaveRelationshipDTO(
                                UUID.fromString(actionUserId),
                                actionUserId + "," + relationshipWithId,
                                1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Invite recipient can only accept requests or block users"));
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
    public void declineRelationshipRequest() throws Exception {
        String uniqueId = "508081af-5ba5-4318-b678-983e103a78f3,609b08a3-356d-40d8-9a87-b4e1d47abf4d";

        mvc
                .perform(post("/declineRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new DeclineRelationshipRequest(uniqueId)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));

        assertTrue(repository.findRelationshipsByUniqueId(uniqueId).isEmpty());
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
    public void cannotDeclineOtherUsersRelationship() throws Exception {
        String uniqueId = "508081af-5ba5-4318-b678-983e103a78f3,a4b35fdd-441e-4691-9a03-cb0b2a4822a2";

        mvc
                .perform(post("/declineRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(asJsonString(new DeclineRelationshipRequest(uniqueId)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Only own relationships can be declined."));
    }

}
