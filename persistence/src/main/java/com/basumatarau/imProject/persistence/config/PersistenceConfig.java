package com.basumatarau.imProject.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"com.basumatarau.imProject.persistence.repository"})
@EntityScan(basePackages = {"com.basumatarau.imProject.persistence.model"})
@ComponentScan(basePackages = "com.basumatarau.imProject.persistence")
public class PersistenceConfig {
}
