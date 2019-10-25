module com.basumatarau.imProject.persistence {

    requires org.hibernate.orm.core;

    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;
    requires spring.data.commons;
    requires spring.data.jpa;

    requires java.sql;
    requires java.desktop;
    requires java.annotation;
    requires java.persistence;

    exports com.basumatarau.imProject.persistence.repository.util;
    exports com.basumatarau.imProject.persistence.repository;
    exports com.basumatarau.imProject.persistence.model;
    exports com.basumatarau.imProject.persistence.config;
}