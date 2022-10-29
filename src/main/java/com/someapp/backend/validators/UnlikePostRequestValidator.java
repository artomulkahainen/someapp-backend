package com.someapp.backend.validators;

import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class UnlikePostRequestValidator implements Validator {

    private final JWTTokenUtil jwtTokenUtil;
    private final PostLikeService postLikeService;

    public UnlikePostRequestValidator(final JWTTokenUtil jwtTokenUtil,
                                      final PostLikeService postLikeService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.postLikeService = postLikeService;
    }

    public boolean supports(final Class<?> clazz) {
        return UnlikePostRequest.class.isAssignableFrom(clazz);
    }

    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final UnlikePostRequest request = (UnlikePostRequest) target;
        final Optional<PostLike> postLike =
                postLikeService.findPostLikeById(request.getUuid());

        if (postLike.isPresent() && !Objects.equals(
                jwtTokenUtil.getIdFromToken(req),
                postLike.get().getUserId())) {
            errors.reject("Only post " +
                    "like owner can unlike the post");
        }
    }
}
