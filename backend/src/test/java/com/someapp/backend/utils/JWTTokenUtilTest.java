package com.someapp.backend.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.someapp.backend.utils.jwt.JWTTokenUtil;
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

    @Mock
    JWTTokenUtil jwtTokenUtil;

    @Test
    public void returnUuidFromToken() {
        when(req.getHeader("Authorization"))
                .thenReturn("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4OGZlODJkZS0wNTY5LTRiNGItODYyOC0xZGFhZDNiYWM3Y2M7bm9sbGEiLCJleHAiOjE2NDE0NjA2NzIsImlhdCI6MTY0MTQ1OTc3Mn0.dLmBf17vTMBMLj-xr8yu5VIN179HgKdcpIQKyjfPtbt3aGYQQbwx67aX3NZC8gh-sxEdgmbbwPfqgH2Kp593Fg");

        assertThat(jwtTokenUtil.getIdFromToken(req)).isEqualTo("88fe82de-0569-4b4b-8628-1daad3bac7cc");
    }

}
