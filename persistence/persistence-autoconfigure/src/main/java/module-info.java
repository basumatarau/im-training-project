module com.basumatarau.imProject.persistence.autoconfigure {

    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;

    requires lombok;
    requires com.basumatarau.imProject.persistence.lib;

    requires java.persistence;
    requires java.sql;
    requires spring.orm;
    requires spring.jdbc;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.data.commons;

    exports com.basumatarau.imProject.persistence.autoConfig;
}