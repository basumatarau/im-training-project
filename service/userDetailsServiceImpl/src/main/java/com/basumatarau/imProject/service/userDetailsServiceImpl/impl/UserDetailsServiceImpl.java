package com.basumatarau.imProject.service.userDetailsServiceImpl.impl;

import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userRepository
                .findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("user name not found: " + s));

        return convertToUserDetailsImpl(user);
    }

    private UserDetails convertToUserDetailsImpl(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .disabled(!user.getEnabled())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(buildGrantedAuthorities(user))
                .build();
    }

    private GrantedAuthority[] buildGrantedAuthorities(User user) {
        return new GrantedAuthority[]{new SimpleGrantedAuthority(user.getRole().toString())};
    }
}
