package com.someapp.backend.services;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.extendedclasses.ExtendedUser;
import com.someapp.backend.util.extendedinterfaces.ExtendedUserDetails;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.FindUserByNameRequest;
import com.someapp.backend.util.responses.UserNameIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ExtendedUserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new ExtendedUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(user.isAdmin() ? new SimpleGrantedAuthority("ADMIN") : new SimpleGrantedAuthority("USER"))
        );
    }

    public User findOwnUserDetails(HttpServletRequest req) {
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(req.getHeader("Authorization").substring(7));
        return userRepository.findByUsername(usernameFromToken);
    }

    public List<UserNameIdResponse> findUsersByName(FindUserByNameRequest findUserByNameRequest) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username").descending());

        return userRepository.findAll(pageable).stream()
                .filter(user -> user.getUsername().contains(findUserByNameRequest.getUsername()))
                .map(user -> new UserNameIdResponse(user.getUsername(), user.getId()))
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
