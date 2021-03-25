package com.someapp.backend.controllers;

import com.someapp.backend.entities.Post;
import com.someapp.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }
}
