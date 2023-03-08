package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.repositories.PostCommentRepository;
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
public class PostCommentDeleteDTOValidator implements Validator {

    private final PostCommentRepository postCommentRepository;
    private final JWTTokenUtil jwtTokenUtil;

    public PostCommentDeleteDTOValidator(
            final PostCommentRepository postCommentRepository,
            final JWTTokenUtil jwtTokenUtil) {
        this.postCommentRepository = postCommentRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return PostCommentDeleteDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        final PostCommentDeleteDTO postCommentDeleteDTO =
                (PostCommentDeleteDTO) target;

        final Optional<PostComment> postComment =
                postCommentRepository.findById(postCommentDeleteDTO.getUuid());

        if (postComment.isPresent()
                && !postComment.get().getUserId().equals(actionUserId)) {
            errors.reject(
                    "User can delete only their own post comments");
        }
    }
}
