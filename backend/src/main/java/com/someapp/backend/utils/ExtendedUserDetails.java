package com.someapp.backend.utils;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface ExtendedUserDetails extends UserDetails {

    UUID getId();

}
