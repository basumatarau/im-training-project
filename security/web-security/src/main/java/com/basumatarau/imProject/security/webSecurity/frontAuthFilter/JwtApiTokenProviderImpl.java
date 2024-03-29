package com.basumatarau.imProject.security.webSecurity.frontAuthFilter;

import com.basumatarau.imProject.security.webSecurity.customPrincipal.AppUserDetails;
import com.basumatarau.imProject.security.webSecurity.oauth2.user.AbstractCustomOAuth2UserImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtApiTokenProviderImpl {

    private static final Logger log = LoggerFactory.getLogger(JwtApiTokenProviderImpl.class);

    @Value("${app.auth.tokenPrefix:Bearer }")
    private String tokenPrefix;

    @Value("${app.auth.tokenExpirationMsec}")
    private Long tokenExpirationPeriod;

    private Key secureKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public String createToken(Authentication auth){
        AppUserDetails principal = ((AppUserDetails) auth.getPrincipal());
        Date expiryDate = new Date(new Date().getTime() + tokenExpirationPeriod);

        return Jwts.builder()
                .setSubject(principal.getAppId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secureKey)
                .compact();
    }

    public Long getAppUserIdFromToken(String token){
        Claims body = Jwts.parser()
                .setSigningKey(secureKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(body.getSubject());
    }

    public boolean validate(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secureKey).parseClaimsJws(token.replace(tokenPrefix, ""));
            return true;
        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return false;
    }
}
