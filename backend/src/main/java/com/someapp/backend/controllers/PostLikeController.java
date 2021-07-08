package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.repositories.PostLikeRepository;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.LikePostRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.validators.RelationshipValidator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PostLikeController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    RelationshipValidator relationshipValidator;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @PostMapping("/likePostByUsingPOST")
    public PostLike likePost(HttpServletRequest req, @Valid @RequestBody LikePostRequest likePostRequest,
                             BindingResult bindingResult) throws BindException {

        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));

        // IF VALIDATION ERRORS, THROW AN ERROR
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

            // CHECK IF ACTION USER AND USER WHO MADE THE POST ARE FRIENDS
        } else if (!relationshipValidator.isActiveRelationship(actionUserId, likePostRequest.getPostUserId())) {
            throw new BadArgumentException("Relationship with given users is not active");

            // IF POSTLIKE REPOSITORY ALREADY CONTAINS THE LIKE, THROW AN ERROR
        } else if (!postLikeRepository.findAll().stream()
                .filter(postLike -> postLike.getPostId().equals(likePostRequest.getPostId())
                        && postLike.getUserId().equals(actionUserId))
                .collect(Collectors.toList()).isEmpty()) {
            throw new BadArgumentException("Post like is already found with given uuids");

            // IF BOTH POSTID AND USERID ARE CORRECT, SAVE NEW POST LIKE
        } else if (postRepository.findById(likePostRequest.getPostId()).isPresent()
                && userRepository.findById(likePostRequest.getPostUserId()).isPresent()) {
            return postLikeRepository.save(
                    new PostLike(postRepository.getById(likePostRequest.getPostId()),
                            userRepository.getById(likePostRequest.getPostUserId())));

            // IF POST WAS NOT FOUND, THROW AN ERROR
        } else {
            throw new ResourceNotFoundException("Post or user was not found with given uuid");
        }
    }

    /*@DeleteMapping("/deleteLikeByUsingPOST")
    public UUIDRequest deleteLike(@PathVariable UUID postLikeId) {
        if (postLikeRepository.findById(postLikeId).isPresent()) {
            postLikeRepository.deleteById(postLikeId);
            return new UUIDRequest(postLikeId);
        } else {
            throw new ResourceNotFoundException("Post or user was not found with given uuid");
        }
    }*/
}
