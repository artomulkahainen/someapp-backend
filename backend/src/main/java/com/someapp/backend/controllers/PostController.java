package com.someapp.backend.controllers;

import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.interfaces.api.PostApi;
import com.someapp.backend.services.PostService;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PostController implements PostApi {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<PostDTO> getPostsByRelationships(HttpServletRequest req) {
        return postMapper.mapPostsToPostDTOs(postService.findPostsByRelationships(req));
    }

    @Override
    public PostDTO sendPost(HttpServletRequest req, SendPostRequest sendPostRequest,
                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postMapper.mapPostToPostDTO(postService.save(req, sendPostRequest));
    }

    @Override
    public DeleteResponse deletePost(HttpServletRequest req,
                                     DeletePostRequest deletePostRequest,
                                     BindingResult bindingResult) throws BindException {
        // ADD VALIDATOR TO PREVENT DELETING OTHER USER'S POSTS
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postService.delete(req, deletePostRequest);
    }

}
