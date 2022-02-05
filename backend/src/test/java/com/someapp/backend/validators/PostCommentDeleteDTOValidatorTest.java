package com.someapp.backend.validators;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
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
public class PostCommentDeleteDTOValidatorTest {

    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @InjectMocks
    private PostCommentDeleteDTOValidator validator;

    private Errors errors;
    private PostCommentDeleteDTO dto;

    @Before
    public void setup() {
        dto = new PostCommentDeleteDTO(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0"));
        errors = new BeanPropertyBindingResult(dto, "postCommentDeleteDTO");
    }

    @Test
    public void cantDeletePostComment_IfItsNotWrittenByLoggedInUser() {
        User user = new User("teppo", "heppu");
        user.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        when(postCommentRepository.findById(dto.getUuid())).thenReturn(Optional.of(new PostComment("nice", new Post(), user)));

        validator.validate(dto, errors);
        assertTrue(errors.hasErrors());
        assertThat(errors.getAllErrors().get(0).getCode())
                .isEqualTo("User can delete only their own post comments");
    }

}
