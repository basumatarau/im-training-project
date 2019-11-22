package com.basumatarau.imProject.persistence.autoConfig;

import com.basumatarau.imProject.persistence.lib.repository.*;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ConditionalOnMissingBean(
        value = {
                UserRepository.class,
                ChatRoomRepository.class,
                DistributedMessageRepository.class,
                MessageResourceRepository.class,
                PrivateMessageRepository.class,
                UserRepository.class
        })
@EnableConfigurationProperties(InstantMessengerPersistenceProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.basumatarau.imProject.persistence.lib.repository")
@AutoConfigurationPackage
public class InstantMessengerPersistenceAutoConfiguration {

    private final InstantMessengerPersistenceProperties env;

    public InstantMessengerPersistenceAutoConfiguration(
            InstantMessengerPersistenceProperties instantMessengerPersistenceProperties) {

        this.env = instantMessengerPersistenceProperties;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getDatasource().driverClassName));
        dataSource.setUrl(env.getDatasource().url);
        dataSource.setUsername(env.getDatasource().username);
        dataSource.setPassword(env.getDatasource().password);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.basumatarau.imProject.persistence.lib.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getSessionFactory().hbm2ddlAuto);
        hibernateProperties.setProperty("hibernate.dialect", env.getSessionFactory().dialect);
        hibernateProperties.setProperty("hibernate.show_sql", env.getSessionFactory().showSql.toString());
        hibernateProperties.setProperty("hibernate.format_sql", env.getSessionFactory().formatSql.toString());
        return hibernateProperties;
    }

}
