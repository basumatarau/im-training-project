package com.basumatarau.imProject.security.webSecurity.oauth2;

import com.basumatarau.imProject.security.webSecurity.customPrincipal.AppUserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AppOAuth2User extends OAuth2User, AppUserDetails {
    String getProvidedId();
    String gteProvider();
}
