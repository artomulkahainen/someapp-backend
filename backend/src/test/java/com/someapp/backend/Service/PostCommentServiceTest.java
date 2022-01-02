package com.someapp.backend.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.someapp.backend.services.PostCommentServiceImpl;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.validators.RelationshipValidator;
import com.someapp.backend.util.validators.UserPostValidator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public class PostCommentServiceTest {

    private UserPostValidator userPostValidator;
    private RelationshipValidator relationshipValidator;
    private PostCommentServiceImpl postCommentService;
    private JWTTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setupService() {
        userPostValidator = mock(UserPostValidator.class);
        relationshipValidator = mock(RelationshipValidator.class);
        postCommentService = mock(PostCommentServiceImpl.class);
        jwtTokenUtil = mock(JWTTokenUtil.class);

        skipValidators();
        mockActionUserId();
    }

    @Test
    public void testSave() {

    }

    private void skipValidators() {
        when(userPostValidator.isValid(any(), any())).thenReturn(true);
        when(relationshipValidator.isActiveRelationship(any(), any())).thenReturn(false);
    }

    private void mockActionUserId() {
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString(""));
    }
}
