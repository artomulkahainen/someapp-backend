package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.utils.requests.UUIDRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface PostCommentApi {

    @PostMapping("/sendPostCommentByUsingPOST")
    PostComment sendNewPostComment(@NotNull HttpServletRequest req, @Valid @RequestBody PostCommentSaveDTO postCommentSaveDTO,
                                   BindingResult bindingResult) throws BindException;

    @PostMapping("/deletePostCommentByUsingPOST")
    DeleteResponse deletePostCommentById(@NotNull HttpServletRequest req,
                                         @Valid @RequestBody PostCommentDeleteDTO postCommentDeleteDTO,
                                         BindingResult bindingResult) throws BindException;
}
