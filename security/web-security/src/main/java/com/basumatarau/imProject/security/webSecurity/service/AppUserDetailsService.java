package com.basumatarau.imProject.security.webSecurity.service;

import com.basumatarau.imProject.security.webSecurity.customPrincipal.AppLocalUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface AppUserDetailsService
        extends UserDetailsService {
    Optional<AppLocalUserDetails> loadUserById(Long id) throws EntityNotFoundException;
}
