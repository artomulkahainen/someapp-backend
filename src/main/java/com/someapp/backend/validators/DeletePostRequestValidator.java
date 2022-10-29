package com.someapp.backend.validators;

import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.services.PostService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
public class DeletePostRequestValidator implements Validator {

    private final PostService postService;
    private final JWTTokenUtil jwtTokenUtil;

    public DeletePostRequestValidator(final PostService postService,
                                      final JWTTokenUtil jwtTokenUtil) {
        this.postService = postService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return DeletePostRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final DeletePostRequest request = (DeletePostRequest) target;
        final Optional<Post> postToDelete =
                postService.findPostById(request.getUuid());
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        if (postToDelete.isPresent()
                && !postToDelete.get().getUserId().equals(actionUserId)) {
            errors.reject("Post can be deleted only" +
                    " by post creator.");
        }
    }


}
