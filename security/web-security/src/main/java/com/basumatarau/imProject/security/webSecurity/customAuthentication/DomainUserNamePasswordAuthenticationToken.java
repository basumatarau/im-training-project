package com.basumatarau.imProject.security.webSecurity.customAuthentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class DomainUserNamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String domainName;

    public DomainUserNamePasswordAuthenticationToken(Object principal, Object credentials, String domainName) {
        super(principal, credentials);
        this.setDomainName(domainName);
    }

    public DomainUserNamePasswordAuthenticationToken(Object principal,
                                                     Object credentials,
                                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
