package com.basumatarau.imProject.security.webSecurity.frontAuthFilter;

import com.basumatarau.imProject.security.webSecurity.customPrincipal.CustomUserDetails;
import com.basumatarau.imProject.security.webSecurity.service.CustomUserDetailsService;
import com.basumatarau.imProject.security.webSecurity.util.TokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${application.security.auth.headerName:Authorization}")
    private String jwtTokenHeaderName;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private JwtApiTokenProviderImpl jwtApiTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = httpServletRequest.getHeader(jwtTokenHeaderName);

        if(!StringUtils.isEmpty(authHeader)){
            String rawToken = tokenExtractor.extract(authHeader);

            if(jwtApiTokenProvider.validate(rawToken)){
                String email = jwtApiTokenProvider.getUserEmailFromToken(rawToken);

                CustomUserDetails customUserDetails = customUserDetailsService
                        .loadUserByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("no user found by email: " + email));

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails,
                                null, customUserDetails.getAuthorities()
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
