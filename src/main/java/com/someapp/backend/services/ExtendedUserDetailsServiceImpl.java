package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.utils.ExtendedUserDetails;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.dto.UserNameIdResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExtendedUserDetailsServiceImpl implements ExtendedUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ExtendedUserDetails loadUserByUsername(final String username) {

        final Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new ExtendedUser(
                user.get().getId(),
                user.get().getUsername(),
                user.get().getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(user.get().isAdmin() ? new SimpleGrantedAuthority("ADMIN")
                        : new SimpleGrantedAuthority("USER"))
        );
    }

    public User findOwnUserDetails() {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final String usernameFromToken = jwtTokenUtil.getUsernameFromToken(req);
        return userRepository.findByUsername(usernameFromToken).orElseThrow(RuntimeException::new);
    }

    public List<UserNameIdResponse> findUsersByName(final FindUserByNameRequest findUserByNameRequest) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().contains(findUserByNameRequest.getUsername()))
                .map(user -> new UserNameIdResponse(user.getId(), user.getUsername()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Optional<User> findUserById(final UUID uuid) {
        return userRepository.findById(uuid);
    }

    public User save(final User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BadArgumentException("Given values are not suitable for user account");
        }
    }

    @Transactional
    public DeleteResponse deleteUser(final DeleteUserRequest request) {
        final User user = userRepository.findById(request.getUuid())
                .orElseThrow(ResourceNotFoundException::new);

        // Delete all relationships with deleted user
        relationshipRepository.deleteAll(relationshipRepository.findRelationshipsByRelationshipWith(user.getUUID()));

        // Delete user
        userRepository.delete(user);
        return new DeleteResponse(request.getUuid(), "Successfully deleted user");
    }

}
