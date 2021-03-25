package com.someapp.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(new User("urpo", "urpoOnTurpo"));
        }
    }

    @Test
    public void findUsersIsSuccessful() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyCollectionOf(User.class))));
    }

    @Test
    public void creatingUserIsSuccessful() throws Exception {
        mockMvc.perform(post("/users")
                .content(asJsonString(new User("kusti", "kustipojke")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("kusti"));
    }

    @Test
    public void creatingVeryShortUsernameIsNotPossible() throws Exception {
        mockMvc.perform(post("/users")
                .content(asJsonString(new User("k", "aaaa")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void creatingVeryShortPasswordIsNotPossible() throws Exception {
        mockMvc.perform(post("/users")
                .content(asJsonString(new User("kaija", "ko")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void notPossibleToCreateUserWithExistingUsername() throws Exception {
        mockMvc.perform(post("/users")
                .content(asJsonString(new User("urpo", "urpoOnTurpo")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
