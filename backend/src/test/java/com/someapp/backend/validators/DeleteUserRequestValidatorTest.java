package com.someapp.backend.validators;

import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class DeleteUserRequestValidatorTest {

    @Mock
    JWTTokenUtil jwtTokenUtil;
    @InjectMocks
    DeleteUserRequestValidator validator;

    @Test
    public void userCannotDeleteOtherUsersAccounts() {
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("9ed27d1a-7c85-4442-8b60-44037f4c91d6"));
        Errors errors = new BeanPropertyBindingResult(request, "deleteUserRequest");
        when(jwtTokenUtil.getIdFromToken(any())).thenReturn(UUID.fromString("f4d94673-7ce6-41b2-af50-60154f471118"));
        validator.validate(request, errors);

        assertTrue(!errors.getAllErrors().isEmpty());
        assertTrue(Objects.equals(errors.getAllErrors().get(0).getCode(),
                "User can only delete their own user account"));
    }
}
