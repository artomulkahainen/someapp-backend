package com.someapp.backend.validators;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.dto.UnlikePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class UnlikePostRequestValidator implements Validator {

    @Autowired
    private HttpServletRequest req;
    private JWTTokenUtil jwtTokenUtil;
    private PostLikeService postLikeService;

    public UnlikePostRequestValidator(JWTTokenUtil jwtTokenUtil, PostLikeService postLikeService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.postLikeService = postLikeService;
    }

    public boolean supports(Class<?> clazz) {
        return UnlikePostRequest.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        UnlikePostRequest request = (UnlikePostRequest) target;
        Optional<PostLike> postLike = postLikeService.findPostLikeById(request.getUuid());

        if (postLike.isPresent() && !Objects.equals(jwtTokenUtil.getIdFromToken(req), postLike.get().getUserId())) {
            errors.reject("Only post like owner can unlike the post");
        }
    }
}
