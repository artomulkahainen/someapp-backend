package com.someapp.backend.services;

import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.entities.extendedclasses.ExtendedUser;
import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.FindUserByNameRequest;
import com.someapp.backend.dto.UserNameIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExtendedUserDetailsServiceImpl implements ExtendedUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

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

    public User findOwnUserDetails(HttpServletRequest req) {
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(req.getHeader("Authorization"));
        return userRepository.findByUsername(usernameFromToken).orElseThrow(RuntimeException::new);
    }

    public List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username").descending());

        return userRepository.findAll(pageable).stream()
                .filter(user -> user.getUsername().contains(findUserByNameRequest.getUsername()))
                .map(user -> new UserNameIdResponse(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public User save(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BadArgumentException("Given values are not suitable for user account");
        }
    }

}
