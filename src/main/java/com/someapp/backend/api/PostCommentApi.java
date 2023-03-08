package com.someapp.backend.api;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface PostCommentApi {

    @PostMapping("/sendPostCommentByUsingPOST")
    PostCommentDTO sendNewPostComment(
            @Valid @RequestBody PostCommentSaveDTO postCommentSaveDTO,
            BindingResult bindingResult) throws BindException;

    @PostMapping("/deletePostCommentByUsingPOST")
    DeleteResponse deletePostCommentById(
            @Valid @RequestBody PostCommentDeleteDTO postCommentDeleteDTO,
            BindingResult bindingResult) throws BindException;
}
