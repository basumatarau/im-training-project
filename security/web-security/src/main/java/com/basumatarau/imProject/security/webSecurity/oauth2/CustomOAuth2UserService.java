package com.basumatarau.imProject.security.webSecurity.oauth2;

import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.exception.Oauth2AuthenticationProcessingException;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import by.vironit.training.basumatarau.messenger.security.CustomUserDetailsService;
import by.vironit.training.basumatarau.messenger.security.impl.CustomUserDetailsImpl;
import by.vironit.training.basumatarau.messenger.security.oauth2.user.OAuth2UserInfo;
import by.vironit.training.basumatarau.messenger.security.oauth2.user.OAuth2UserInfoFactory;
import com.basumatarau.imProject.persistence.model.user.AuthenticationDetails;
import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import com.basumatarau.imProject.security.webSecurity.exception.Oauth2AuthenticationProcessingException;
import com.basumatarau.imProject.security.webSecurity.oauth2.user.OAuth2UserInfo;
import com.basumatarau.imProject.security.webSecurity.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Transactional
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId, oAuth2User.getAttributes()
        );

        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new Oauth2AuthenticationProcessingException("Authentication authority doesn't have the user email, " +
                    "auth id: " + registrationId);
        }
        AuthenticationDetails.AuthenticationProvider authProviderIdFromRequest =
                AuthenticationDetails.AuthenticationProvider
                    .valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        //map for side effect (register new acc) and return details, orElse: update account
        return userRepository
                        .findByEmail(oAuth2UserInfo.getEmail())
                        .map(
                                retrievedUser -> {
                                    if(!retrievedUser.getDetails().getAuthProvider().name().equals(authProviderIdFromRequest.toString())){
                                        throw new Oauth2AuthenticationProcessingException("Looks like you're signed up with " +
                                                authProviderIdFromRequest.toString() + " account. Please use your " +
                                                retrievedUser.getDetails().getAuthProvider() + " to sign in.");
                                    }

                                    retrievedUser.setNickName(oAuth2UserInfo.getName());
                                    retrievedUser.getDetails().(oAuth2UserInfo.getImageUrl());

                                    userRepository.save(retrievedUser);

                                    return retrievedUser;
                                }
                        ).orElseGet(
                            () -> {

                                User newUser = new User().userBuilder()
                                        .role(User.UserRole.USER)
                                        .isEnabled(true)
                                        .details()
                                        .nickName(oAuth2UserInfo.getName())
                                        .email(oAuth2UserInfo.getEmail())
                                        .authProvider(authProviderIdFromRequest)
                                        .providerId(oAuth2UserInfo.getId())
                                        .build();

                                User persistedUser = userRepository.save(newUser);

                                return new CustomUserDetailsImpl(
                                        persistedUser.getId(),
                                        persistedUser.getProviderId(),
                                        persistedUser.getPasswordHash(),
                                        persistedUser.getEmail(),
                                        persistedUser.getEnabled(),
                                        Collections.singletonList(new SimpleGrantedAuthority(persistedUser.getRole().name())),
                                        oAuth2User.getAttributes());
                            }
                        );
    }
}
