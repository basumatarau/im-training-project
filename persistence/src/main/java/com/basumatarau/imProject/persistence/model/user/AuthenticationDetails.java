package com.basumatarau.imProject.persistence.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@NoArgsConstructor
@Data
@Embeddable
public class AuthenticationDetails {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthenticationProvider authProvider;

    private String providedId;

    @EqualsAndHashCode.Exclude
    private String imageUrl;

    @Transient
    @EqualsAndHashCode.Exclude
    private Map<String, Object> attributes;

    public enum AuthenticationProvider{
        google,
        github,
        local
    }

    @Builder
    public AuthenticationDetails(AuthenticationProvider authProvider, String providedId, String imageUrl) {
        this.authProvider = authProvider;
        this.providedId = providedId;
        this.imageUrl = imageUrl;
    }

    @Builder(builderMethodName = "transientBuilder")
    public AuthenticationDetails(AuthenticationProvider authProvider, String providedId, String imageUrl, Map<String, Object> attributes) {
        this.authProvider = authProvider;
        this.providedId = providedId;
        this.imageUrl = imageUrl;
        this.attributes = attributes;
    }
}
