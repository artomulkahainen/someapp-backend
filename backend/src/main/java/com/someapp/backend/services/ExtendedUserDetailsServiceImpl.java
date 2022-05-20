package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.entities.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExtendedUserDetailsServiceImpl implements ExtendedUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest req;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ExtendedUserDetails loadUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

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
                Arrays.asList(user.get().isAdmin() ? new SimpleGrantedAuthority("ADMIN") : new SimpleGrantedAuthority("USER"))
        );
    }

    public User findOwnUserDetails() {
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(req);
        return userRepository.findByUsername(usernameFromToken).orElseThrow(RuntimeException::new);
    }

    public List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().contains(findUserByNameRequest.getUsername()))
                .map(user -> new UserNameIdResponse(user.getId(), user.getUsername()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Optional<User> findUserById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public User save(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BadArgumentException("Given values are not suitable for user account");
        }
    }

    public DeleteResponse deleteUser(DeleteUserRequest request) {
        User user = userRepository.findById(request.getUuid()).orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(user);
        return new DeleteResponse(request.getUuid(), "Successfully deleted user");
    }

}
