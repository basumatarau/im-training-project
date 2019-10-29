package com.basumatarau.imProject.security.webSecurity.oauth2.user;

import java.util.Map;

import com.basumatarau.imProject.persistence.model.user.AuthenticationDetails;
import static com.basumatarau.imProject.persistence.model.user.AuthenticationDetails.AuthenticationProvider.github;
import static com.basumatarau.imProject.persistence.model.user.AuthenticationDetails.AuthenticationProvider.google;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (AuthenticationDetails.AuthenticationProvider.valueOf(registrationId)){
            case google:
                return new GoogleOAuth2UserInfo(attributes);
            case github:
                return new GithubOAuth2UserInfo(attributes);
            default:
                throw new RuntimeException("registrationId not allowed (no client registration details provided)");
        }
    }
}
