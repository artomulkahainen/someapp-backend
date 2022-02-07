package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.services.ExtendedUserDetailsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostMapperTest {

    private PostCommentMapper postCommentMapper = mock(PostCommentMapper.class);
    private ExtendedUserDetailsServiceImpl userDetailsService = mock(ExtendedUserDetailsServiceImpl.class);

    @Spy
    private PostMapper postMapper = new PostMapper(userDetailsService, postCommentMapper);

    @Test
    public void mapPostsToPostDTOs() {
        List<Post> posts = ImmutableList.of(new Post(), new Post());
        List<String[]> values = ImmutableList.of(new String[]{"9ed27d1a-7c85-4442-8b60-44037f4c91d6", "blaablaa", "f4d94673-7ce6-41b2-af50-60154f471118"},
                new String[]{"d2d7ab98-ada4-4a82-87a8-f74993f95612", "blaablaa2", "e2c81fc7-24d2-4ec9-84e1-d6924046fee0"});

        for (int i = 0; i < posts.size(); i++) {
            posts.get(i).setUUID(UUID.fromString(values.get(i)[0]));
            posts.get(i).setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            posts.get(i).setPost(values.get(i)[1]);
            User user = new User();
            user.setUUID(UUID.fromString(values.get(i)[2]));
            posts.get(i).setUser(user);
            posts.get(i).setPostComments(ImmutableList.of());
            posts.get(i).setPostLikes(ImmutableList.of());
        }

        List<PostDTO> postDTOS = postMapper.mapPostsToPostDTOs(posts);
        assertTrue(postDTOS.get(0).getPost().equals("blaablaa"));
        assertTrue(postDTOS.get(1).getUserUuid().equals(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0")));
    }

    @Test
    public void mapPostToPostDTO() {
        Post newPost = new Post("jepajee", new User());
        newPost.getUser().setUUID(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0"));
        newPost.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        newPost.setPostComments(ImmutableList.of());
        newPost.setPostLikes(ImmutableList.of());

        PostDTO postDTO = postMapper.mapPostToPostDTO(newPost);
        assertTrue(postDTO.getPost().equals("jepajee"));
        assertTrue(postDTO.getUserUuid().equals(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0")));
        assertTrue(newPost.getPostLikes().isEmpty());
    }

    @Test
    public void mapSendPostRequestToPost() {
        User user = new User();
        user.setUUID(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0"));
        when(userDetailsService.findUserById(any())).thenReturn(Optional.of(user));

        SendPostRequest sendPostRequest = new SendPostRequest("jeaa");
        Post post = postMapper.mapSendPostRequestToPost(user.getUUID(), sendPostRequest);
        assertTrue(post.getPost().equals("jeaa"));
        assertTrue(post.getUser().getUUID().equals(user.getUUID()));
    }

}
