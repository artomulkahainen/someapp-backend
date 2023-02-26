package com.someapp.backend.IT;

import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.dto.UserNameIdResponse;
import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.dto.FindUserByNameRequest;
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
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;
    private MockMvc mvc;
    private String token;

    @Before
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
    public void findUsersIsSuccessful() throws Exception {
        mvc.perform(post("/findUsersByNameByUsingPOST")
                .content(Format.asJsonString(new FindUserByNameRequest("yybe")))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(UserNameIdResponse.class))))
                .andExpect(jsonPath("$.[0].username").value("yyberi"))
                .andExpect(jsonPath("$.[1].username").value("kyyberi"));
    }

    @Test
    @WithMockUser(username = "kalleKustaa")
    @Transactional
    @Sql(value = {"/db/users.sql", "/db/relationships.sql", "/db/posts.sql", "/db/postlikes.sql"})
    public void findOwnUserDetailsIsSuccessful() throws Exception {
        mvc.perform(get("/findOwnUserDetailsByUsingGET")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value("609b08a3-356d-40d8-9a87-b4e1d47abf4d"))
                .andExpect(jsonPath("$.username").value("kalleKustaa"))
                .andExpect(jsonPath("$.posts[0].post").value("Nice stuff"))
                .andExpect(jsonPath("$.relationships[0].uniqueId")
                        .value("508081af-5ba5-4318-b678-983e103a78f3,609b08a3-356d-40d8-9a87-b4e1d47abf4d"))
                .andExpect(jsonPath("$.likedPostsIds[0].postId")
                        .value("434811ee-f31f-4929-beec-194f237cf417"));
    }

    @Test
    @Transactional
    @Sql("/db/users.sql")
    public void creatingUserIsSuccessful() throws Exception {
        mvc.perform(post("/saveNewUserByUsingPOST")
                .content(Format.asJsonString(new UserSaveDTO("kusti", "kustipojke")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("kusti"));

        assertTrue(repository.findByUsername("kusti").isPresent());
    }

    @Test
    @Transactional
    @Sql("/db/users.sql")
    public void cannotCreateUserWithExistingUsername() throws Exception {
        mvc.perform(post("/saveNewUserByUsingPOST")
                        .content(Format.asJsonString(new UserSaveDTO("yyberi", "kustipojke")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Username is already in use"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "kalleKustaa")
    @Sql(value = { "/db/users.sql", "/db/relationships.sql", "/db/posts.sql", "/db/postlikes.sql"})
    public void deletingOwnUserIsSuccessful() throws Exception {
        UUID userId = UUID.fromString("609b08a3-356d-40d8-9a87-b4e1d47abf4d");
        mvc.perform(post("/deleteUserByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(Format.asJsonString(new DeleteUserRequest(
                                userId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(repository.findByUsername("kalleKustaa").isPresent());
        assertFalse(postRepository.findAll().stream().anyMatch(post -> post.getUserId().equals(userId)));
        assertTrue(relationshipRepository.findRelationshipsByuser_id(userId).isEmpty());
        assertFalse(postLikeRepository.findById(UUID.fromString("724df059-7e0c-43b2-a2a4-3375605d4098")).isPresent());
        assertFalse(relationshipRepository.findAll().stream().anyMatch(rs -> rs.getRelationshipWith().equals(userId)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "kalleKustaa")
    @Sql("/db/users.sql")
    public void cannotDeleteOtherUser() throws Exception {
        UUID userId = UUID.fromString("12bffa51-9899-497b-b41a-2b71d8c42629");
        mvc.perform(post("/deleteUserByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(Format.asJsonString(new DeleteUserRequest(
                                userId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("User can only delete their own user account"));
    }

}
