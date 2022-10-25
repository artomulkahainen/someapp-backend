package com.someapp.backend.validators;

import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.PostService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class DeletePostRequestValidatorTest {

    @Mock
    private PostService postService;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @InjectMocks
    private DeletePostRequestValidator validator;

    @Test
    public void cantDeletePost_ifItsNotWrittenByPostCreator() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

        User user = new User("pelle", "peloton");
        user.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        Post post = new Post("Let's play Rocket League", user);
        post.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        when(postService.findPostById(any())).thenReturn(Optional.of(post));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));

        DeletePostRequest request = new DeletePostRequest(post.getUUID());
        Errors errors = new BeanPropertyBindingResult(request, "deletePostRequest");
        validator.validate(request, errors);
        assertTrue(!errors.getAllErrors().isEmpty());
        assertThat(errors.getAllErrors().get(0).getCode()).isEqualTo("Post can be deleted only by post creator.");
    }

}
