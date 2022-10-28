package com.someapp.backend.validators;

import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSaveDTOValidator implements Validator {

    private final UserRepository userRepository;

    public UserSaveDTOValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSaveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final UserSaveDTO userSaveDTO = (UserSaveDTO) target;

        // USERNAME IS UNIQUE
        if (userRepository.findByUsername(userSaveDTO.getUsername()).isPresent()) {
            errors.reject("Username is already in use");
        }

        // USERNAME IS AT LEAST THREE CHARACTERS LONG
        if (userSaveDTO.getUsername() == null || userSaveDTO.getUsername().length() < 3) {
            errors.reject("Username must be at least three characters long.");
        }

        // PASSWORD IS AT LEAST THREE CHARACTERS LONG
        if (userSaveDTO.getPassword() == null || userSaveDTO.getPassword().length() < 3) {
            errors.reject("Password must be at least three characters long.");
        }
    }
}
