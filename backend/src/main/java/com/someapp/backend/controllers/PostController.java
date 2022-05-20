package com.someapp.backend.controllers;

import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.interfaces.api.PostApi;
import com.someapp.backend.services.PostService;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.validators.DeletePostRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController implements PostApi {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private DeletePostRequestValidator validator;

    @Override
    public List<PostDTO> getPostsByRelationships() {
        return postMapper.mapPostsToPostDTOs(postService.findPostsByRelationships());
    }

    @Override
    public PostDTO sendPost(SendPostRequest sendPostRequest,
                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postMapper.mapPostToPostDTO(postService.save(sendPostRequest));
    }

    @Override
    public DeleteResponse deletePost(DeletePostRequest deletePostRequest,
                                     BindingResult bindingResult) throws BindException {
        validator.validate(deletePostRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postService.delete(deletePostRequest);
    }

}
