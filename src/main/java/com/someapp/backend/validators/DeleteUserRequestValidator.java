package com.someapp.backend.validators;

import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class DeleteUserRequestValidator implements Validator {

    private final JWTTokenUtil jwtTokenUtil;

    public DeleteUserRequestValidator(final JWTTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean supports(Class<?> clazz) {
        return DeleteUserRequest.class.isAssignableFrom(clazz);
    }

    public void validate(final Object target, final Errors errors) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        final DeleteUserRequest request = (DeleteUserRequest) target;

        if (!Objects.equals(jwtTokenUtil.getIdFromToken(req), request.getUuid())) {
            errors.reject("User can only delete their own user account");
        }
    }
}
