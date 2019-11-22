package com.basumatarau.imProject.service.userDetailsServiceImpl.config;

import com.basumatarau.imProject.persistence.config.InstantMessengerPersistenceAutoConfig;
import com.basumatarau.imProject.service.userDetailsServiceImpl.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(value = {
        InstantMessengerPersistenceAutoConfig.class})
@ComponentScan(basePackageClasses = {UserDetailsServiceImpl.class})
public class UserDetailsServiceImplConfig {
}
