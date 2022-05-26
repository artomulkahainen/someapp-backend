package com.someapp.backend.IT;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.utils.requests.LoginRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationshipControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private String token;

    private User loginUser;
    private User anotherUser;

    @Before
    @Transactional
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // FILL THE DB WITH USERS
        loginUser = new User("urpo", bCryptPasswordEncoder.encode("urpoOnTurpo"));
        anotherUser = new User("kalle", bCryptPasswordEncoder.encode("kalleEiOoTurpo"));
        userRepository.save(loginUser);
        userRepository.save(anotherUser);

        // LOGIN FIRST AND STORE THE TOKEN FROM LOGIN
        MockHttpServletResponse response = mvc
                .perform(post("/loginByUsingPOST")
                        .content(Format.asJsonString(new LoginRequest("urpo", "urpoOnTurpo")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        token = new JSONObject(response.getContentAsString()).getString("token");
    }

    @Test
    @WithMockUser(username = "urpo")
    @Transactional
    public void sendNewRelationshipRequestIsSuccessful() throws Exception {
        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(Format.asJsonString(new SaveRelationshipDTO(
                                anotherUser.getUUID(),
                                loginUser.getUUID().toString() + "," + anotherUser.getUUID().toString(),
                                0)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
