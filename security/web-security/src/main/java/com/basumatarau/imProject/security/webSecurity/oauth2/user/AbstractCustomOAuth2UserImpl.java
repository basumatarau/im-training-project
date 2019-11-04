package com.basumatarau.imProject.security.webSecurity.oauth2.user;

import com.basumatarau.imProject.security.webSecurity.oauth2.AppOAuth2User;

import java.util.Map;

public abstract class AbstractCustomOAuth2UserImpl implements AppOAuth2User {

    public enum AuthenticationProvider{
        google,
        github,
        local
    }

    protected AuthenticationProvider provider;

    protected Long appId;

    protected Map<String, Object> attributes;

    public AbstractCustomOAuth2UserImpl(Map<String, Object> attributes,
                                        AuthenticationProvider provider) {
        this.provider = provider;
        this.attributes = attributes;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public Long getAppId(){
        if(appId == null){
            throw new RuntimeException("no appId set");
        }
        return appId;
    }

    @Override
    public abstract String gteProvider();

    @Override
    public abstract String getProvidedId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
