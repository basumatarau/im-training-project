package com.basumatarau.imProject.web.config;

import com.basumatarau.imProject.security.webSecurity.config.WebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {WebSecurityConfiguration.class})
public class WebConfig {
}
