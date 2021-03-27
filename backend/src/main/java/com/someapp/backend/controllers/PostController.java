package com.someapp.backend.controllers;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.requests.SendPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/posts/{uuid}")
    public Post getOnePost(@PathVariable("uuid") UUID uuid) throws ResourceNotFoundException {
        try {
            return postRepository.findById(uuid).get();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Post was not found with given uuid");
        }
    }

    // All users posts
    @GetMapping("/posts/user/{userId}")
    public List<Post> getAllUsersPosts(@PathVariable UUID userId) throws ResourceNotFoundException {
        try {
            List<Post> posts = postRepository
                    .findAll();

            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getUserId() == userId) {
                    System.out.println("userId is found from posts!");
                }
            }

            return postRepository
                    .findAll()
                    .stream()
                    .filter(post -> post.getUserId() == userId)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            System.out.println("post was not found");
            throw new ResourceNotFoundException("Post was not found with given uuid");
        }
    }

    @PostMapping("/posts")
    public Post sendPost(@Valid @RequestBody SendPostRequest sendPostRequest, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
            throw new BindException(bindingResult);
        }

        try {
            User user = userRepository.getById(sendPostRequest.getUserId());
            return postRepository.save(new Post(sendPostRequest.getPost(), user));
        } catch (Exception e) {
            throw new ResourceNotFoundException("No user found with given uuid");
        }
    }

}
