package com.someapp.backend.controllers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.services.PostServiceImpl;
import com.someapp.backend.util.mappers.PostMapper;
import com.someapp.backend.util.requests.DeletePostRequest;
import com.someapp.backend.util.requests.SendPostRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    private PostMapper postMapper;

    @GetMapping("/getPostsByRelationshipsByUsingGET")
    public List<PostDTO> getPostsByRelationships(HttpServletRequest req) {
        List<Post> posts = postService.findPostsByRelationships(req);
        return posts
                .stream()
                .map(post -> postMapper.mapPostToPostDTO(post))
                .collect(ImmutableList.toImmutableList());
    }

    @PostMapping("/sendNewPostByUsingPOST")
    public PostDTO sendPost(HttpServletRequest req, @Valid @RequestBody SendPostRequest sendPostRequest,
                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Post post = postService.save(req, sendPostRequest);
        return postMapper.mapPostToPostDTO(post);
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
