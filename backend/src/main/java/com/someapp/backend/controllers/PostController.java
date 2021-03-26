package com.someapp.backend.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
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

    /*@PostMapping("/posts")
    public Post sendPost(@Valid @RequestBody ObjectNode jsonNode) throws Exception {
        String post = jsonNode.get("post").asText();
        Optional<User> user = userRepository.findById(UUID.fromString(jsonNode.get("userId").asText()));

        if (!user.isPresent()) {
            throw new ResourceNotFoundException("No user found with given uuid");
        } else {
            try {
                return postRepository.save(new Post(post, user.get()));
            } catch (Exception e) {
                if (post.length() < 1 || post.length() > 250) {
                    throw new BadArgumentException("Post must contain 1-250 letters.");
                } else {
                    throw new Exception();
                }
            }
        }
    }*/
    /*@PostMapping("/posts")
    public Post sendPost(@Valid @RequestBody Post post) throws Exception {
        if (userRepository.findById(post.getUserId()).isPresent()) {
            return postRepository.save(post);
        } else {
            throw new ResourceNotFoundException("No user found with given uuid");
        }
    }*/

}
