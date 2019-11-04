package com.basumatarau.imProject.security.webSecurity.service.impl;

import com.basumatarau.imProject.persistence.model.user.AuthenticationDetails;
import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import com.basumatarau.imProject.security.webSecurity.exception.Oauth2AuthenticationProcessingException;
import com.basumatarau.imProject.security.webSecurity.oauth2.user.AbstractCustomOAuth2UserImpl;
import com.basumatarau.imProject.security.webSecurity.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService {

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

        AbstractCustomOAuth2UserImpl customOAuth2User = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId, oAuth2User.getAttributes()
        );

        if(StringUtils.isEmpty(customOAuth2User.getEmail())) {
            throw new Oauth2AuthenticationProcessingException("Authentication authority doesn't have the user email, " +
                    "auth id: " + registrationId);
        }
        AbstractCustomOAuth2UserImpl.AuthenticationProvider authProviderIdFromRequest =
                AbstractCustomOAuth2UserImpl.AuthenticationProvider
                    .valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        //map for side effect (register new acc) and return details, orElse: update account
        User freshUser = userRepository
                .findByProviderAndProvidedId(customOAuth2User.gteProvider(), customOAuth2User.getProvidedId())
                .map(
                        refreshUser(customOAuth2User, authProviderIdFromRequest)
                ).orElseGet(
                        createFreshUser(customOAuth2User, authProviderIdFromRequest)
                );

        return customOAuth2User;
    }

    private Supplier<User> createFreshUser(AbstractCustomOAuth2UserImpl oAuth2UserInfo,
                                           AbstractCustomOAuth2UserImpl.AuthenticationProvider authProviderIdFromRequest) {
        return () -> {

            AuthenticationDetails details = AuthenticationDetails
                    .builder()
                    .authProvider(authProviderIdFromRequest.name())
                    .imageUrl(oAuth2UserInfo.getImageUrl())
                    .providedId(oAuth2UserInfo.getProvidedId())
                    .build();

            User newUser = User.userBuilder()
                    .role(User.UserRole.USER)
                    .isEnabled(true)
                    .details(details)
                    .nickName(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .build();

            return userRepository.save(newUser);
        };
    }

    private Function<User, User> refreshUser(AbstractCustomOAuth2UserImpl oAuth2UserInfo,
                                             AbstractCustomOAuth2UserImpl.AuthenticationProvider authProviderIdFromRequest) {
        return retrievedUser -> {
            if (!retrievedUser.getDetails().getProvider().equals(authProviderIdFromRequest.name())) {
                throw new Oauth2AuthenticationProcessingException("Looks like you're signed up with " +
                        authProviderIdFromRequest.toString() + " account. Please use your " +
                        retrievedUser.getDetails().getProvider() + " account to sign in.");
            }

            retrievedUser.setNickName(oAuth2UserInfo.getName());
            AuthenticationDetails details = retrievedUser.getDetails();
            details.setImageUrl(oAuth2UserInfo.getImageUrl());
            retrievedUser.setDetails(details);
            userRepository.save(retrievedUser);
            return retrievedUser;
        };
    }
}
