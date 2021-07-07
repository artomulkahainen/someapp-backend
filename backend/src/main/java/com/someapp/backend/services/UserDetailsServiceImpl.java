package com.someapp.backend.services;

import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.extendedclasses.ExtendedUser;
import com.someapp.backend.util.extendedinterfaces.ExtendedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

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
}
