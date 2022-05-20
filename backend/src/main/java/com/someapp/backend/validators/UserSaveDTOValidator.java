package com.someapp.backend.validators;

import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSaveDTOValidator implements Validator {

    private final UserRepository userRepository;

    public UserSaveDTOValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSaveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final UserSaveDTO userSaveDTO = (UserSaveDTO) target;

        // USERNAME IS UNIQUE
        if (userRepository.findByUsername(userSaveDTO.getUsername()).isPresent()) {
            errors.reject("Username is already in use");
        }
    }
}
