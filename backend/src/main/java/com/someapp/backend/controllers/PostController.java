package com.someapp.backend.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/posts")
    public Post sendPost(@Valid @RequestBody ObjectNode jsonNode) throws Exception {
        String post = jsonNode.get("post").asText();
        Optional<User> user = userRepository.findById(UUID.fromString(jsonNode.get("userId").asText()));

        if (!user.isPresent()) {
            throw new CustomExceptions.BadArgumentsException("Repository doesn't contain user with given uuid.");
        } else {
            try {
                return postRepository.save(new Post(post, user.get()));
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }
}
