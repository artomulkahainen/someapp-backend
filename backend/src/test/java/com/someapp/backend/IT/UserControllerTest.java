package com.someapp.backend.IT;

import com.someapp.backend.dto.UserNameIdResponse;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
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
import org.springframework.web.context.WebApplicationContext;

import static com.someapp.backend.testUtility.Format.asJsonString;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;
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
    @Sql(value = {"/db/users.sql", "/db/relationships.sql"})
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
                .andExpect(jsonPath("$.posts").isEmpty())
                .andExpect(jsonPath("$.relationships[0].uniqueId")
                        .value("508081af-5ba5-4318-b678-983e103a78f3,609b08a3-356d-40d8-9a87-b4e1d47abf4d"));
    }

    /*@Test
    public void creatingUserIsSuccessful() throws Exception {
        mockMvc.perform(post("/saveNewUserByUsingPOST")
                .content(Format.asJsonString(new User("kusti", "kustipojke")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("kusti"));
    }

    @Test
    public void creatingVeryShortUsernameIsNotPossible() throws Exception {
        mockMvc.perform(post("/saveNewUserByUsingPOST")
                .content(Format.asJsonString(new User("k", "aaaa")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Username length must be between 3-15 letters"));
    }

    @Test
    public void creatingVeryLongUsernameIsNotPossible() throws Exception {
        mockMvc.perform(post("/saveNewUserByUsingPOST")
                .header("Authorization", "asd")
                .content(Format.asJsonString(new User("kaarlekaarlekaaggr", "aaaa")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Username length must be between 3-15 letters"));
    }

    @Test
    public void creatingVeryShortPasswordIsNotPossible() throws Exception {
        mockMvc.perform(post("/saveNewUserByUsingPOST")
                .content(Format.asJsonString(new User("kaija", "ko")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Password must be longer or equal to 3"));
    }
    @Test
    public void notPossibleToCreateUserWithExistingUsername() throws Exception {
        mockMvc.perform(post("/saveNewUserByUsingPOST")
                .content(Format.asJsonString(new User("urpo", "urpoOnTurpo")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }*/

}
