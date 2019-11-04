package com.basumatarau.imProject.security.webSecurity.service.impl;

import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import com.basumatarau.imProject.security.webSecurity.customPrincipal.AppLocalUserDetails;
import com.basumatarau.imProject.security.webSecurity.customPrincipal.CustomUserPrincipal;
import com.basumatarau.imProject.security.webSecurity.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final User user = userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("not found user by name: " + name));

        return convertToCustomUserDetails(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLocalUserDetails> loadUserById(Long id) throws EntityNotFoundException {
        return userRepository
                .findById(id)
                .map(this::convertToCustomUserDetails);
    }

    private AppLocalUserDetails convertToCustomUserDetails(User user){
        return CustomUserPrincipal.builder()
                .accountConfirmed(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(user.getIsEnabled())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())))
                .credentialsNonExpired(true)
                //
                .id(user.getId())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .registrationId(user.getDetails().getProvider())
                .userName(user.getNickName())
                .build();
    }

}
