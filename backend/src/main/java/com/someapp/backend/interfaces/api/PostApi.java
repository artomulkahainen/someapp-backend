package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.dto.SendPostRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface PostApi {

    @GetMapping("/getPostsByRelationshipsByUsingGET")
    List<PostDTO> getPostsByRelationships();

    @PostMapping("/sendNewPostByUsingPOST")
    PostDTO sendPost(@Valid @RequestBody SendPostRequest sendPostRequest,
                     BindingResult bindingResult) throws BindException;

    @PostMapping("/deletePostByUsingPOST")
    DeleteResponse deletePost(@Valid @RequestBody DeletePostRequest deletePostRequest,
                              BindingResult bindingResult) throws BindException;
}
