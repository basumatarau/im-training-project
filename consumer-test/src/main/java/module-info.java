module com.basumatarau.imProject.consumer {

    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.beans;
    requires spring.context;

    requires java.sql;
    requires java.desktop;
    requires java.annotation;

    requires com.basumatarau.imProject.persistence.starter;
    requires com.basumatarau.imProject.persistence.autoConfig;
    requires com.basumatarau.imProject.persistence.lib;
}