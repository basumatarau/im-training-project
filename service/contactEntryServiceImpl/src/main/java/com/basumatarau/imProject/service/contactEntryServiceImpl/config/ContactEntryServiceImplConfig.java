package com.basumatarau.imProject.service.contactEntryServiceImpl.config;

import com.basumatarau.imProject.convertor.config.ModelMapperConfig;
import com.basumatarau.imProject.persistence.config.PersistenceConfig;
import com.basumatarau.imProject.serializer.config.SerializerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        PersistenceConfig.class,
        SerializerConfig.class,
        ModelMapperConfig.class})
@ComponentScan(basePackages = {"com.basumatarau.imProject.service.contactEntryServiceImpl.impl"})
public class ContactEntryServiceImplConfig {
}
