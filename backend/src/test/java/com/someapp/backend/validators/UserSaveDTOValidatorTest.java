package com.someapp.backend.validators;

import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserSaveDTOValidatorTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserSaveDTOValidator validator;

    private Errors errors;
    private UserSaveDTO userSaveDTO;

    @Before
    public void setup() {
        userSaveDTO = new UserSaveDTO("nice", "guy");
        errors = new BeanPropertyBindingResult(userSaveDTO, "userSaveDTO");
    }

    @Test
    public void cantCreateUser_WithAlreadyExistingUsername() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        validator.validate(userSaveDTO, errors);
        assertTrue(errors.hasErrors());
        assertThat(errors.getAllErrors().get(0).getCode())
                .isEqualTo("Username is already in use");
    }

    @Test
    public void userCanBeCreated_IfItsValid() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        validator.validate(userSaveDTO, errors);
        assertFalse(errors.hasErrors());
    }
}
