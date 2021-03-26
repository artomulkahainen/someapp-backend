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

@RequestMapping("posts")
@RestController("posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{uuid}")
    public Post getOnePost(@PathVariable("uuid") String uuid) throws ResourceNotFoundException {
        try {
            return postRepository.getById(UUID.fromString(uuid));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Post was not found with given uuid");
        }
    }

    @PostMapping("/")
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
