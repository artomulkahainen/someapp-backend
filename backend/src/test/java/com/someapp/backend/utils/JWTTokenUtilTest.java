package com.someapp.backend.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.assertj.core.api.Assertions.assertThat;
import static com.someapp.backend.testUtility.jwt.JWTTokenUtil.getIdFromToken;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class JWTTokenUtilTest {

    @Mock
    HttpServletRequest req;

    @Test
    public void returnUuidFromToken() {
        when(req.getHeader("Authorization")).thenReturn("Bearer ");

        assertThat(getIdFromToken(req)).isEqualTo("");
    }

}
