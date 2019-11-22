package com.basumatarau.imProject.persistence.autoConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties(prefix = "persistence")
@ConditionalOnProperty(name = "persistence", matchIfMissing = true)
public class InstantMessengerPersistenceProperties {

    @NestedConfigurationProperty
    public ApplicationDataSourceProperties datasource = new ApplicationDataSourceProperties();

    @NestedConfigurationProperty
    public ApplicationEntityManagerFactoryProperties sessionFactory = new ApplicationEntityManagerFactoryProperties();

    @NestedConfigurationProperty
    private ApplicationTransactionManagerProperties txManager = new ApplicationTransactionManagerProperties();

    public static class ApplicationDataSourceProperties {
        public String url = "jdbc:h2:mem:testDB;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_UPPER=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS instant_messenger_db_schema;";
        public String username = "sa";
        public String password = "sa";
        public String driverClassName = "org.h2.Driver";
        public Boolean testWhileIdle = Boolean.TRUE;
        public String validationQuery = "SELECT 1";

        @NestedConfigurationProperty
        public ConnectionPool conPool;

        @Getter
        @Setter
        public static class ConnectionPool{
            public Integer connectionTimeout = 30000;
            public Integer idleTimeout = 600000;
            public Integer maxLifetime = 1800000;
            public Integer maximumPoolSize = 20;
        }
    }

    @Getter
    @Setter
    public static class ApplicationEntityManagerFactoryProperties {
        public String hbm2ddlAuto = "create-drop";
        public String dialect = "org.hibernate.dialect.H2Dialect";
        public Boolean showSql = Boolean.TRUE;
        public Boolean formatSql = Boolean.TRUE;
    }
    @Getter
    @Setter
    public static class ApplicationTransactionManagerProperties {

    }
}
