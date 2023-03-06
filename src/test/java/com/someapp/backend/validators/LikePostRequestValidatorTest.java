package com.someapp.backend.validators;

import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.services.PostLikeServiceImpl;
import com.someapp.backend.services.RelationshipServiceImpl;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class LikePostRequestValidatorTest {

    @Mock
    private PostLikeServiceImpl postLikeService;
    @Mock
    private RelationshipServiceImpl relationshipService;
    @Mock
    private RelationshipRepository repository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @InjectMocks
    private LikePostRequestValidator validator;

    private Errors errors;
    private LikePostRequest likePostRequest;
    private Relationship relationship;
    private Post post;
    private User user1;
    private User user2;

    @Before
    public void setup() {
        errors = new BeanPropertyBindingResult(likePostRequest, "likePostRequest");
        user1 = new User();
        user1.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        user2 = new User();
        user2.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        post = new Post("blaablaa", user1);
        post.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        relationship = new Relationship(user1, user2.getUUID(),
                user1.getUUID().toString() + "," + user2.getUUID().toString(), 1);
        likePostRequest = new LikePostRequest(post.getUUID());
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(user1.getUUID());
        when(postRepository.findById(post.getUUID())).thenReturn(Optional.of(post));
    }

    @Test
    public void cannotLikePost_ifRelationshipIsNotFound() {
        when(postLikeService.likeAlreadyExists(any(), any())).thenReturn(false);
        when(postRepository.findById(any())).thenReturn(Optional.of(new Post("ss", user2)));
        validator.validate(likePostRequest, errors);
        assertTrue(errors.hasErrors());
        assertThat(errors.getAllErrors().get(0).getCode())
                .isEqualTo("Action user and post creator user doesn't have active relationship");
    }

    @Test
    public void cannotLikePost_ifLikeIsAlreadyFound() {
        when(postLikeService.likeAlreadyExists(any(), any())).thenReturn(true);
        validator.validate(likePostRequest, errors);
        assertTrue(errors.hasErrors());
        assertThat(errors.getAllErrors().get(0).getCode())
                .isEqualTo("Post is already liked by the action user");
    }
}
