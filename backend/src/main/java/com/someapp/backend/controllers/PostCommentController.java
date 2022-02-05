package com.someapp.backend.controllers;

import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.interfaces.api.PostCommentApi;
import com.someapp.backend.services.PostCommentService;
import com.someapp.backend.utils.requests.UUIDRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
import com.someapp.backend.validators.PostCommentDeleteDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostCommentController implements PostCommentApi {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private PostCommentDeleteDTOValidator deleteDTOValidator;

    @Override
    public PostComment sendNewPostComment(HttpServletRequest req, PostCommentSaveDTO postCommentSaveDTO,
                                          BindingResult bindingResult) throws BindException {
        // IF VALIDATION ERRORS
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postCommentService.save(req, postCommentSaveDTO);
    }

    @Override
    public DeleteResponse deletePostCommentById(HttpServletRequest req,
                                                PostCommentDeleteDTO postCommentDeleteDTO,
                                                BindingResult bindingResult) throws BindException {
        deleteDTOValidator.validate(postCommentDeleteDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postCommentService.delete(req, postCommentDeleteDTO.getUuid());
    }
}
