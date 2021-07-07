package com.someapp.backend.util.extendedinterfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface ExtendedUserDetails extends UserDetails {

    UUID getId();

}
