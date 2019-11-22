package com.basumatarau.imProject.service.userServiceImpl.config;

import com.basumatarau.imProject.convertor.config.ModelMapperConfig;
import com.basumatarau.imProject.persistence.config.InstantMessengerPersistenceAutoConfig;
import com.basumatarau.imProject.serializer.config.SerializerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        InstantMessengerPersistenceAutoConfig.class,
        SerializerConfig.class,
        ModelMapperConfig.class})
@ComponentScan(basePackages = "com.basumatarau.imProject.service.userServiceImpl.impl")
public class UserServiceImplConfig {

}
