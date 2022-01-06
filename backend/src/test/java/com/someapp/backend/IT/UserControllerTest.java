package com.someapp.backend.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.testUtility.Format;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.utils.responses.UserNameIdResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Value("${env.HEADER_STRING}")
    String headerString;

    @Before
    public void setup() {
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(new User("urpo", "urpoOnTurpo"));
            userRepository.save(new User("kalle", "kalleEiOoTurpo"));
        }
    }

    @Test
    public void findUsersIsSuccessful() throws Exception {
        mockMvc.perform(post("/findUsersByNameByUsingPOST")
                .content(Format.asJsonString(new FindUserByNameRequest("kal")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(UserNameIdResponse.class))));
    }

    /*@Test
    public void findOwnUserDetailsIsSuccessful() throws Exception {

        when(tokenMock.getUsernameFromToken(anyString())).thenReturn("urpo");
        when(userRepoMock.findByUsername(anyString())).thenReturn(new User("korppi", "kotka"));

        mockMvc.perform(get("/findOwnUserDetailsByUsingGET"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(User.class))));
    }*/

    @Test
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
                .content(Format.asJsonString(new User("kaarlekaarlekaar", "aaaa")))
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
    }

}
