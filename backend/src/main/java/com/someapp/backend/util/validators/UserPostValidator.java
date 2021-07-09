package com.someapp.backend.util.validators;

import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserPostValidator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public boolean isValid(UUID postId, UUID userId) {
        return postRepository.findById(postId).isPresent() && userRepository.findById(userId).isPresent();
    }
}
