package com.someapp.backend.IT;

import com.someapp.backend.dto.SaveRelationshipDTO;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelationshipControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "yyberi")
    @Test
    public void acceptRelationshipIsSuccessful() throws Exception {
        mvc
                .perform(post("/saveNewRelationshipByUsingPOST")
                        .with(request -> {
                            request.addHeader("Authorization", "nice;5345346");
                            return request;
                        })
                        .content(Format.asJsonString(new SaveRelationshipDTO(
                                UUID.fromString("316d7af3-3f53-40a5-bdbd-8db5b9e301a7"),
                                "d10a121d-e5ad-4301-9fe6-cfbf4aa91147,316d7af3-3f53-40a5-bdbd-8db5b9e301a7",
                                1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
