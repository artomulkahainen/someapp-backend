package com.someapp.backend.controllers;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.services.PostServiceImpl;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.requests.DeletePostRequest;
import com.someapp.backend.util.requests.SendPostRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    /* NEED TO IMPLEMENT THIS
    @PostMapping("/getPostsByRelationshipsByUsingPOST")
    public List<Post> getPostsByRelationships() {

    } NEED TO IMPLEMENT THIS */

    @PostMapping("/sendNewPostByUsingPOST")
    public Post sendPost(HttpServletRequest req, @Valid @RequestBody SendPostRequest sendPostRequest,
                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postService.save(req, sendPostRequest);
    }

    @PostMapping("/deletePostByUsingPOST")
    public DeleteResponse deletePost(HttpServletRequest req,
                                     @Valid @RequestBody DeletePostRequest deletePostRequest,
                                     BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postService.delete(req, deletePostRequest);
    }

}
