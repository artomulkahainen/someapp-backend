package com.someapp.backend.controllers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.entities.Post;
import com.someapp.backend.interfaces.api.PostApi;
import com.someapp.backend.services.PostService;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.utils.requests.DeletePostRequest;
import com.someapp.backend.utils.requests.SendPostRequest;
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
        List<Post> posts = postService.findPostsByRelationships(req);
        return posts
                .stream()
                .map(post -> postMapper.mapPostToPostDTO(post))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public PostDTO sendPost(HttpServletRequest req, SendPostRequest sendPostRequest,
                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Post post = postService.save(req, sendPostRequest);
        return postMapper.mapPostToPostDTO(post);
    }

    @Override
    public DeleteResponse deletePost(HttpServletRequest req,
                                     DeletePostRequest deletePostRequest,
                                     BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postService.delete(req, deletePostRequest);
    }

}
