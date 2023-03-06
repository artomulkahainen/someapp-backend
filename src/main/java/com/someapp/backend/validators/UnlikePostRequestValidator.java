package com.someapp.backend.validators;

import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.services.PostService;
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
    private final PostService postService;

    public UnlikePostRequestValidator(final JWTTokenUtil jwtTokenUtil,
                                      final PostService postService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.postService = postService;
    }

    public boolean supports(final Class<?> clazz) {
        return UnlikePostRequest.class.isAssignableFrom(clazz);
    }

    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final UnlikePostRequest request = (UnlikePostRequest) target;
        final Optional<Post> postToUnlike = postService.findPostById(request.getUuid());

        if (postToUnlike.isEmpty()) {
            errors.reject("Unlike post failed");
            return;
        }

        if (postToUnlike.get().getPostLikes().stream().noneMatch(
                like -> like.getUserId().equals(jwtTokenUtil.getIdFromToken(req)))) {
            errors.reject("Only post " +
                    "like owner can unlike the post");
        }
    }
}
