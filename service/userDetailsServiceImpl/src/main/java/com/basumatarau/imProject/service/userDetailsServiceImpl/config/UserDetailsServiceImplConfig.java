package com.basumatarau.imProject.service.userDetailsServiceImpl.config;

import com.basumatarau.imProject.persistence.config.PersistenceConfig;
import com.basumatarau.imProject.service.userDetailsServiceImpl.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(value = {
        PersistenceConfig.class})
@ComponentScan(basePackageClasses = {UserDetailsServiceImpl.class})
public class UserDetailsServiceImplConfig {
}
