package com.someapp.backend.IT;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = { "test" })
@TestPropertySource(properties = { "env.EXPIRATION_TIME=900000", "env.SECRET=jea" })
public class RelationshipControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    private String token;

    private MockMvc mvc;

    @Before
    public void setup() {
        jwtTokenUtil = new JWTTokenUtil();
        token = jwtTokenUtil.generateToken(
                new ExtendedUser(UUID.fromString("6e2867f4-1b59-46a4-b54d-f118e077a52a"),
                        "arto",
                        "salasana",
                        ImmutableList.of(new SimpleGrantedAuthority("USER"))));

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "yyberi")
    public void sendNewRelationshipRequestIsSuccessful() throws Exception {
        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "Bearer " + token);
                            return request;
                        })
                        .content(Format.asJsonString(new SaveRelationshipDTO(
                                UUID.fromString("316d7af3-3f53-40a5-bdbd-8db5b9e301a7"),
                                "6e2867f4-1b59-46a4-b54d-f118e077a52a,316d7af3-3f53-40a5-bdbd-8db5b9e301a7",
                                0)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
