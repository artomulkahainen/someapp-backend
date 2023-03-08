package com.someapp.backend.controllers;

import com.someapp.backend.dto.PostCommentDTO;
import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.api.PostCommentApi;
import com.someapp.backend.mappers.PostCommentMapper;
import com.someapp.backend.services.PostCommentService;
import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.validators.PostCommentDeleteDTOValidator;
import com.someapp.backend.validators.PostCommentSaveDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostCommentController implements PostCommentApi {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private PostCommentSaveDTOValidator saveValidator;

    @Autowired
    private PostCommentDeleteDTOValidator deleteValidator;

    @Autowired
    private PostCommentMapper postCommentMapper;

    @Override
    public PostCommentDTO sendNewPostComment(
            final PostCommentSaveDTO postCommentSaveDTO,
            final BindingResult bindingResult) throws BindException {
        saveValidator.validate(postCommentSaveDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postCommentMapper.mapPostCommentToPostCommentDTO(postCommentService.save(postCommentSaveDTO));
    }

    @Override
    public DeleteResponse deletePostCommentById(
            final PostCommentDeleteDTO postCommentDeleteDTO,
            final BindingResult bindingResult) throws BindException {
        deleteValidator.validate(postCommentDeleteDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postCommentService.delete(postCommentDeleteDTO.getUuid());
    }
}
