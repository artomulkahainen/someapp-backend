package com.someapp.backend.mappers;

import static org.junit.Assert.assertTrue;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.entities.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PostCommentMapperTest {

    @InjectMocks
    PostCommentMapper postCommentMapper;

    @Test
    public void mapPostCommentToPostCommentDTOSuccessfully() {
        User user = new User("craig", "smith");
        user.setUUID(UUID.fromString("d2d7ab98-ada4-4a82-87a8-f74993f95612"));
        User commentUser = new User("bailey", "davidson");
        commentUser.setUUID(UUID.fromString("e2c81fc7-24d2-4ec9-84e1-d6924046fee0"));
        Post post = new Post("Very nice times", user);
        post.setUUID(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        PostComment postComment = new PostComment("Very well said", post, commentUser);
        postComment.setUUID(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));

        PostCommentDTO postCommentDTO = postCommentMapper.mapPostCommentToPostCommentDTO(postComment);

        assertTrue(!postCommentDTO.getPostComment().isEmpty());
        assertTrue(postCommentDTO.getPostId() != null);
        assertTrue(postCommentDTO.getUserId() != null);
        assertTrue(postCommentDTO.getUuid() != null);
    }

}
