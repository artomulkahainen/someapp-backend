package com.someapp.backend.services;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.mappers.PostCommentMapper;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest req;
    @Mock
    private PostRepository postRepository;
    @Mock
    private RelationshipRepository relationshipRepository;
    @InjectMocks
    private PostServiceImpl postService;

    private User user;
    private User anotherUser;
    private User anotherOtherUser;
    private Post post;
    private Post anotherPost;
    private Post anotherOtherPost;
    private ExtendedUserDetailsService userDetailsService = mock(ExtendedUserDetailsService.class);
    private PostCommentMapper postCommentMapper = mock(PostCommentMapper.class);
    @Spy
    private PostMapper postMapper = new PostMapper(userDetailsService, postCommentMapper);

    @Before
    public void setup() {
        user = new User("helper", "user");
        anotherUser = new User("fake", "user");
        anotherOtherUser = new User("david", "lynch");
        user.setUUID(UUID.fromString("5fda13b9-e0d3-46e9-ac7b-cf3198fdc198"));
        anotherUser.setUUID(UUID.fromString("6322c70b-239a-462b-89fc-f0f944571362"));
        anotherOtherUser.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        post = new Post("hajaa", user);
        anotherPost = new Post("hola", anotherUser);
        anotherOtherPost = new Post("momo", anotherOtherUser);
        post.setUUID(UUID.fromString("784cc861-349b-4e13-af64-6e7eb6f6d376"));
        post.setCreatedDate(new Timestamp(1245346346L));
        anotherPost.setUUID(UUID.fromString("bd5723cc-6880-401f-9f6e-9d1d4f56c6ca"));
        anotherPost.setCreatedDate(new Timestamp(754745757L));
        anotherOtherPost.setUUID(UUID.fromString("dbb36fda-f60e-4000-a68d-bf4ad5008305"));
        anotherOtherPost.setCreatedDate(new Timestamp(546457236236L));

    }

    @Test
    public void saveIsSuccessful() {
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        when(userDetailsService.findUserById(any())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).then(returnsFirstArg());
        SendPostRequest request = new SendPostRequest("hola");

        Post post = postService.save(request);
        assertThat(post.getPost()).isEqualTo("hola");
        assertTrue(post.getUserId().equals(user.getUUID()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveThrowsError_ifUserIsNotFound() {
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        SendPostRequest request = new SendPostRequest("hola");
        postService.save(request);
    }

    @Test
    public void deleteIsSuccessful() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        DeletePostRequest request = new DeletePostRequest(post.getUUID());

        postService.delete(request);
        verify(postRepository, times(1)).delete(post);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteThrowsError_ifPostIsNotFound() {
        DeletePostRequest request = new DeletePostRequest(UUID.fromString("784cc861-349b-4e13-af64-6e7eb6f6d376"));
        postService.delete(request);
    }

    @Test
    public void successfullyFindsPostWithId() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        Optional<Post> post = postService.findPostById(UUID.fromString("784cc861-349b-4e13-af64-6e7eb6f6d376"));
        assertTrue(post.isPresent());
    }

    @Test
    public void findPostReturnsOptionalEmpty_ifNotPostIsFound() {
        Optional<Post> post = postService.findPostById(UUID.fromString("784cc861-349b-4e13-af64-6e7eb6f6d376"));
        assertFalse(post.isPresent());
    }

    @Test
    public void findPostByRelationships_returnsEmptyList_ifNoRelationshipsFound() {
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(user.getUUID());
        when(postRepository.findAll()).thenReturn(ImmutableList.of(post, anotherPost, anotherOtherPost));

        List<Post> posts = postService.findPostsByRelationships();
        assertThat(posts.size()).isEqualTo(0);
    }
}
