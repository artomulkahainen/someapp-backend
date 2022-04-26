package com.someapp.backend.services;

import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class RelationshipServiceTest {

    @Mock
    private JWTTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest req;
    @Mock
    private RelationshipRepository relationshipRepository;
    @InjectMocks
    private RelationshipServiceImpl relationshipService;

    @Test
    void saveIsSuccessful() {
        when(relationshipRepository.findRelationshipsByUniqueId(any())).thenReturn(new ArrayList<>());
    }
}
