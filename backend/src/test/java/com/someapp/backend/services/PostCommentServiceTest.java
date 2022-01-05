package com.someapp.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.assertj.core.api.Assertions.assertThat;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.UUIDRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostCommentServiceTest {

    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest req;
    @InjectMocks
    private PostCommentServiceImpl postCommentService;

    @Test
    public void testSave() {
        when(req.getHeader("Authorization")).thenReturn("fds35352sgdg");
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        when(postRepository.findById(any())).thenReturn(Optional.of(new Post()));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(postCommentRepository.save(any())).then(returnsFirstArg());

        PostComment postComment = postCommentService.save(req,
                new PostCommentSaveDTO("Nice post!",
                        UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"),
                        UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612")));

        assertThat(postComment.getPostComment()).isEqualTo("Nice post!");
    }

    @Test
    public void testDelete() {
        PostComment postComment = new PostComment("Easy", new Post(), new User());
        postComment.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));

        when(req.getHeader("Authorization")).thenReturn("fds35352sgdg");
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        when(postCommentRepository.findById(any())).thenReturn(Optional.of(postComment));

        postCommentService.delete(req, new UUIDRequest(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118")));
        verify(postCommentRepository, times(1))
                .deleteById(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
    }

    /**
     * Create tests for trying to save without post or user
     * Create tests for trying to delete comment with id that is not in db
     */
}
