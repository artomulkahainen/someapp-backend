package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.PostDTO;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.dto.DeleteResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface PostApi {

    @GetMapping("/getPostsByRelationshipsByUsingGET")
    List<PostDTO> getPostsByRelationships(@NotNull HttpServletRequest req);

    @PostMapping("/sendNewPostByUsingPOST")
    PostDTO sendPost(@NotNull HttpServletRequest req, @Valid @RequestBody SendPostRequest sendPostRequest,
                     BindingResult bindingResult) throws BindException;

    @PostMapping("/deletePostByUsingPOST")
    DeleteResponse deletePost(@NotNull HttpServletRequest req,
                              @Valid @RequestBody DeletePostRequest deletePostRequest,
                              BindingResult bindingResult) throws BindException;
}
