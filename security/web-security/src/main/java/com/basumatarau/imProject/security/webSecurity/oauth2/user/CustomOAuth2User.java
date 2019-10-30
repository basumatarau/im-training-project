package com.basumatarau.imProject.security.webSecurity.oauth2.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class CustomOAuth2User implements OAuth2User {

    protected Map<String, Object> attributes;

    public CustomOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
