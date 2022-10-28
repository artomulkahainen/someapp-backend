package com.someapp.backend.services;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.dto.UserNameIdResponse;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ExtendedUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private ExtendedUserDetailsServiceImpl extendedUserDetailsService;

    private List<User> foundUsers;

    @Before
    public void setup() {
        foundUsers = foundUsers();
    }

    @Test
    public void findOwnUserDetailsSuccessfully() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn("donald");
        when(userRepository.findByUsername(any())).thenReturn(
                Optional.of(new User("uncle", "scrooge")));

        User user = extendedUserDetailsService.findOwnUserDetails();
        assertThat(user.getUsername()).isEqualTo("uncle");
    }

    @Test(expected = RuntimeException.class)
    public void findOwnUserDetails_throwsErrorIfUserNotInDb() {
        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn("donald");
        extendedUserDetailsService.findOwnUserDetails();
    }

    @Test
    public void findUsersByNameSuccessfully() {
        FindUserByNameRequest request = new FindUserByNameRequest("rilla");

        when(userRepository.findAll()).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUsername()).isEqualTo("rilla");
    }

    @Test
    public void findUsersByName_returnsMaximumOfTenUsernames() {
        FindUserByNameRequest request = new FindUserByNameRequest("oyota");

        when(userRepository.findAll()).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(10);
        assertThat(users.get(9).getUsername()).isEqualTo("Toyota9");
    }

    @Test
    public void findUsersByName_returnsEmptyList_WhenUsernameNotFoundInDb() {
        FindUserByNameRequest request = new FindUserByNameRequest("zorro");

        when(userRepository.findAll()).thenReturn(foundUsers);

        List<UserNameIdResponse> users = extendedUserDetailsService.findUsersByName(request);
        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    public void saveIsSuccessful() {
        String bCryptHash = "$2a$10$Ju1Esie2dKHJFWeF/WS9LebOTk5P8QCTw6lBMW04qPM0PflB.F.sC";
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn(bCryptHash);
        when(userRepository.save(any())).then(returnsFirstArg());

        User user = extendedUserDetailsService.save(new User("Reindeer", "moro"));
        assertThat(user.getUsername()).isEqualTo("Reindeer");
        assertThat(user.getPassword()).isEqualTo(bCryptHash);
        assertFalse(user.isAdmin());
        assertThat(user.getPostLikes()).isEmpty();
        assertThat(user.getPosts()).isEmpty();
    }

    @Test
    public void deleteIsSuccessful() {
        ExtendedUserDetailsServiceImpl userService = mock(ExtendedUserDetailsServiceImpl.class);
        User user = new User("delete", "meee");
        user.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));

        userService.deleteUser(request);
        verify(userService, times(1)).deleteUser(request);
    }

    private List<User> foundUsers() {
        return ImmutableList.of(
                new User("Max", "Payne"),
                new User("rilla", "noice"),
                new User("uulalaa", "lul"),
                new User("gorilla", "boi"),
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
                new User("Toyota11", "lul"));
    }


}
