package com.someapp.backend.validators;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.PostService;
import com.someapp.backend.services.RelationshipService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostCommentSaveDTOValidatorTest {

    @Mock
    private RelationshipService relationshipService;
    @Mock
    private PostService postService;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @InjectMocks
    private PostCommentSaveDTOValidator validator;

    private Errors errors;
    private PostCommentSaveDTO postCommentSaveDTO;
    private Post post;
    private User user1;
    private User user2;

    @Before
    public void setup() {
        postCommentSaveDTO = new PostCommentSaveDTO("hello",
                UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        errors = new BeanPropertyBindingResult(postCommentSaveDTO, "postCommentSaveDTO");
        user1 = new User();
        user1.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        user2 = new User();
        user2.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        post = new Post("joojoo", user2);
    }

    @Test
    public void cantCommentAPost_IfValidRelationshipIsNotFound() {
        when(relationshipService.usersHaveActiveRelationship(any(), any())).thenReturn(false);
        when(postService.findPostById(any())).thenReturn(Optional.of(post));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));

        validator.validate(postCommentSaveDTO, errors);
        assertTrue(errors.hasErrors());
        assertThat(errors.getAllErrors().get(0).getCode())
                .isEqualTo("Active relationship with post creator is needed to write a comment");
    }

    @Test
    public void ownPostCanBeCommented() {
        when(postService.findPostById(any())).thenReturn(Optional.of(post));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));

        validator.validate(postCommentSaveDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatorPassesIfNothingIsRejected() {
        when(relationshipService.usersHaveActiveRelationship(any(), any())).thenReturn(true);
        when(postService.findPostById(any())).thenReturn(Optional.of(post));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));

        validator.validate(postCommentSaveDTO, errors);
        assertFalse(errors.hasErrors());
    }
}
