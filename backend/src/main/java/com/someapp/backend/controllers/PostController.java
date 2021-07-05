package com.someapp.backend.controllers;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.requests.SendPostRequest;
import com.someapp.backend.util.requests.UUIDRequest;
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

    /* THIS IS NOT PROBABLY NEEDED*/
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /* THIS IS NOT PROBABLY NEEDED
    @GetMapping("/posts/{uuid}")
    public Post getOnePost(@PathVariable UUID uuid) throws ResourceNotFoundException {
        if (postRepository.findById(uuid).isPresent()) {
            return postRepository.findById(uuid).get();
        } else {
            throw new ResourceNotFoundException("Post was not found with given uuid");
        }
    } THIS IS NOT PROBABLY NEEDED*/



    /* NEED TO IMPLEMENT THIS
    @PostMapping("/posts/getPostsByRelationships")
    public List<Post> getPostsByRelationships() {

    } NEED TO IMPLEMENT THIS */

    @PostMapping("/posts")
    public Post sendPost(@Valid @RequestBody SendPostRequest sendPostRequest,
                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        try {
            User user = userRepository.getById(sendPostRequest.getUserId());
            return postRepository.save(new Post(sendPostRequest.getPost(), user));
        } catch (Exception e) {
            throw new ResourceNotFoundException("No user found with given uuid");
        }
    }

    // NEED TO CONVERT THIS TO USE POSTMAPPING
    @DeleteMapping("/posts/{uuid}")
    public UUIDRequest deletePost(@PathVariable UUID uuid) throws ResourceNotFoundException {
        try {
            postRepository.deleteById(uuid);
            return new UUIDRequest(uuid);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Post was not found with given uuid.");
        }
    }

}
