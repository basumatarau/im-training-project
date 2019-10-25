package com.basumatarau.imProject.serializer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages =
        {"com.basumatarau.imProject.serializer.jsonSerializer"})
public class SerializerConfig {
}
