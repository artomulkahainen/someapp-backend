package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.services.PostLikeServiceImpl;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.LikePostRequest;
import com.someapp.backend.util.requests.UnlikePostRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import com.someapp.backend.util.validators.RelationshipValidator;
import com.someapp.backend.util.validators.UserPostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PostLikeController {

    @Autowired
    PostLikeServiceImpl postLikeService;

    @Autowired
    RelationshipValidator relationshipValidator;

    @Autowired
    UserPostValidator userPostValidator;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @PostMapping("/likePostByUsingPOST")
    public PostLike likePost(HttpServletRequest req, @Valid @RequestBody LikePostRequest likePostRequest,
                             BindingResult bindingResult) throws BindException {

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

            // IF ACTION USER AND USER WHO MADE THE POST ARE NOT FRIENDS, THROW AN EXCEPTION
        } else if (!relationshipValidator.isActiveRelationship(actionUserId, likePostRequest.getPostUserId())) {
            throw new BadArgumentException("Relationship with given users is not active");

            // IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, THROW AN EXCEPTION
        } else if (!postLikeService.likeAlreadyExists(actionUserId, likePostRequest)) {
            throw new BadArgumentException("Post like is already found with given uuids");

            // IF BOTH POSTID AND USERID ARE CORRECT, SAVE NEW POST LIKE
        } else if (userPostValidator.isValid(likePostRequest.getPostId(), actionUserId)) {
            return postLikeService.save(likePostRequest, actionUserId);

            // IF POST WAS NOT FOUND, THROW AN EXCEPTION
        } else {
            throw new ResourceNotFoundException("Post or user was not found with given uuid");
        }
    }

    @PostMapping("/unlikePostByUsingPOST")
    public DeleteResponse unlikePost(HttpServletRequest req,
                                     @Valid @RequestBody UnlikePostRequest unlikePostRequest,
                                     BindingResult bindingResult) throws BindException {

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Optional<PostLike> likeToDelete = postLikeService.getLikeById(unlikePostRequest.getPostLikeId());

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

            // IF LIKE IS NOT FOUND, THROW AN EXCEPTION
        } else if (likeToDelete == null) {
            throw new ResourceNotFoundException("Post like was not found");

            // VALIDATE THAT UNLIKING USER IS LIKE'S OWNER
        } else if (!likeToDelete.get().getUserId().equals(actionUserId)) {
            throw new BadArgumentException("Unliking is only possible from the like owner");

            // IF ABOVE CHECKS ARE NOT TRUE, UNLIKE POST
        } else {
            return postLikeService.delete(unlikePostRequest);
        }
    }
}
