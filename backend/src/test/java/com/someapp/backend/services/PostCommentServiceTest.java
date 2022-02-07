package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.mappers.PostCommentMapper;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostCommentServiceTest {

    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest req;
    @InjectMocks
    private PostCommentServiceImpl postCommentService;

    private PostRepository postRepository = mock(PostRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    @Spy
    private PostCommentMapper postCommentMapper = new PostCommentMapper(postRepository, userRepository);

    @Test
    public void saveIsSuccessful() {
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
    public void deleteIsSuccessful() {
        PostComment postComment = new PostComment("Easy", new Post(), new User());
        postComment.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));

        when(postCommentRepository.findById(any())).thenReturn(Optional.of(postComment));

        postCommentService.delete(req, UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        verify(postCommentRepository, times(1))
                .deleteById(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteThrowsError_ifPostCommentIsNotFound() {
        postCommentService.delete(req, UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
    }
}
