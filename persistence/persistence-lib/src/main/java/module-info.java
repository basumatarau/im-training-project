module com.basumatarau.imProject.persistence.lib {

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
    requires lombok;
    requires spring.tx;

    exports com.basumatarau.imProject.persistence.lib.model;
    exports com.basumatarau.imProject.persistence.lib.model.user;
    exports com.basumatarau.imProject.persistence.lib.repository;
    exports com.basumatarau.imProject.persistence.lib.repository.util;
}