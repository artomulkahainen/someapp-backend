package com.someapp.backend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.UserNameIdResponse;
import com.someapp.backend.entities.User;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ExtendedUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest req;
    @InjectMocks
    private ExtendedUserDetailsServiceImpl extendedUserDetailsService;

    @Test
    public void findOwnUserDetailsSuccessfully() {
        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn("donald");
        when(userRepository.findByUsername(any())).thenReturn(
                Optional.of(new User("uncle", "scrooge")));

        User user = extendedUserDetailsService.findOwnUserDetails(req);
        assertThat(user.getUsername()).isEqualTo("uncle");
    }

    @Test(expected = RuntimeException.class)
    public void findOwnUserDetails_throwsErrorIfUserNotInDb() {
        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn("donald");
        extendedUserDetailsService.findOwnUserDetails(req);
    }

    @Test
    public void findUsersByNameSuccessfully() {
        FindUserByNameRequest request = new FindUserByNameRequest("rilla");
        Page<User> foundUsers = foundUsers();

        when(userRepository.findAll(isA(Pageable.class))).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUsername()).isEqualTo("rilla");
    }

    @Test
    public void findUsersByName_returnsMaximumOfTenUsernames() {
        FindUserByNameRequest request = new FindUserByNameRequest("oyota");
        Page<User> foundUsers = new PageImpl<>(ImmutableList.of(
                new User("Toyota", "Payne"),
                new User("Toyota1", "noice"),
                new User("Toyota2", "lul"),
                new User("Toyota3", "boi"),
                new User("Toyota4", "lul"),
                new User("Toyota5", "lul"),
                new User("Toyota6", "lul"),
                new User("Toyota7", "lul"),
                new User("Toyota8", "lul"),
                new User("Toyota9", "lul"),
                new User("Toyota10", "lul"),
                new User("Toyota11", "lul")));

        when(userRepository.findAll(isA(Pageable.class))).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(10);
        assertThat(users.get(9).getUsername()).isEqualTo("Toyota9");
    }

    @Test
    public void findUsersByName_returnsEmptyList_WhenUsernameNotFoundInDb() {
        FindUserByNameRequest request = new FindUserByNameRequest("zorro");
        Page<User> foundUsers = foundUsers();

        when(userRepository.findAll(isA(Pageable.class))).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(0);
    }

    private Page<User> foundUsers() {
        return new PageImpl<>(ImmutableList.of(
                new User("Max", "Payne"),
                new User("rilla", "noice"),
                new User("uulalaa", "lul"),
                new User("gorilla", "boi")));
    }


}
