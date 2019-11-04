package com.basumatarau.imProject.security.webSecurity.oauth2.user;

import java.util.Map;

import static com.basumatarau.imProject.security.webSecurity.oauth2.user.AbstractCustomOAuth2UserImpl.AuthenticationProvider.github;
import static com.basumatarau.imProject.security.webSecurity.oauth2.user.AbstractCustomOAuth2UserImpl.AuthenticationProvider.google;

public class OAuth2UserInfoFactory {

    public static AbstractCustomOAuth2UserImpl getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (AbstractCustomOAuth2UserImpl.AuthenticationProvider.valueOf(registrationId)){
            case google:
                return new GoogleOAuth2UserInfo(attributes, google);
            case github:
                return new GithubOAuth2UserInfo(attributes, github);
            default:
                throw new RuntimeException("registrationId not allowed (no client registration details provided)");
        }
    }
}
