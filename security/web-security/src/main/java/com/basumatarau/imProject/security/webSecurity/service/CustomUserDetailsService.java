package com.basumatarau.imProject.security.webSecurity.service;

import com.basumatarau.imProject.security.webSecurity.customPrincipal.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface CustomUserDetailsService
        extends UserDetailsService {
    Optional<CustomUserDetails> loadUserByEmail(String email) throws EntityNotFoundException;
}
