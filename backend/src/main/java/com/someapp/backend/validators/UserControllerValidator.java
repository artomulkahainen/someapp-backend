package com.someapp.backend.validators;

import com.someapp.backend.controllers.UserController;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

@Component
public class UserControllerValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserController.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty");
        User user = (User) obj;

        if (!userRepository
                .findAll()
                .stream()
                .map(repoUser -> repoUser.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList())
                .isEmpty()) {
            errors.reject("username", "username.nameAlreadyExists");
        }
    }
}
