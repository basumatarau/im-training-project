package com.basumatarau.imProject.security.webSecurity.oauth2.user;

import com.basumatarau.imProject.persistence.model.user.AuthenticationDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleOAuth2UserInfo extends AbstractCustomOAuth2UserImpl {

    private List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER");

    public GoogleOAuth2UserInfo(Map<String, Object> attributes,
                                AuthenticationProvider provider) {
        super(attributes, provider);
    }

    @Override
    public String gteProvider() {
        return provider.name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("sub", this.getProvidedId());
            this.attributes.put("name", this.getName());
            this.attributes.put("email", this.getEmail());
            this.attributes.put("picture", this.getImageUrl());
        }
        return attributes;
    }

    @Override
    public String getProvidedId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

}
