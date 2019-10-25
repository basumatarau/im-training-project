package com.basumatarau.imProject.security.webSecurity.customPrincipal;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@CustomPrincipal
public class CustomUserPrincipal implements CustomUserDetails {
    private Boolean accountNonLocked;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountConfirmed;

    private Long id;

    private Boolean enabled;
    private String email;
    private String userName;
    private String passwordHash;
    private String registrationId;

    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
