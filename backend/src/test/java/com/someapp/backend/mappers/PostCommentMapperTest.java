package com.someapp.backend.mappers;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostCommentMapperTest {

    PostRepository postRepository = mock(PostRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    @Spy
    PostCommentMapper postCommentMapper = new PostCommentMapper(postRepository, userRepository);

    private User user;
    private User commentUser;
    private Post post;
    private PostComment postComment;

    @Before
    public void setup() {
        user = new User("craig", "smith");
        user.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        commentUser = new User("bailey", "davidson");
        commentUser.setUUID(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0"));
        post = new Post("Very nice times", user);
        post.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        postComment = new PostComment("Very well said", post, commentUser);
        postComment.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
    }

    @Test
    public void mapPostCommentToPostCommentDTOSuccessfully() {
        PostCommentDTO postCommentDTO = postCommentMapper.mapPostCommentToPostCommentDTO(postComment);

        assertTrue(!postCommentDTO.getPostComment().isEmpty());
        assertTrue(postCommentDTO.getPostId() != null);
        assertTrue(postCommentDTO.getUserId() != null);
        assertTrue(postCommentDTO.getUuid() != null);
    }

    @Test
    public void mapPostCommentSaveDTOToPostCommentSuccessfully() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        PostCommentSaveDTO dto = new PostCommentSaveDTO("hellurei", post.getUUID(), user.getUUID());
        PostComment postComment = postCommentMapper.mapPostCommentSaveDTOToPostComment(user.getUUID(), dto);

        assertTrue(!postComment.getPostComment().isEmpty());
        assertTrue(postComment.getPostId() == post.getUUID());
        assertTrue(postComment.getUserId() == user.getUUID());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void mapperThrowsError_IfPostIsNotFound() {
        PostCommentSaveDTO dto = new PostCommentSaveDTO("hellurei", post.getUUID(), user.getUUID());
        postCommentMapper.mapPostCommentSaveDTOToPostComment(user.getUUID(), dto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void mapperThrowsError_IfUserIsNotFound() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        PostCommentSaveDTO dto = new PostCommentSaveDTO("hellurei", post.getUUID(), user.getUUID());
        postCommentMapper.mapPostCommentSaveDTOToPostComment(user.getUUID(), dto);
    }

}
