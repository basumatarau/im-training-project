module com.basumatarau.imProject.persistence.starter {

    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;

    requires java.sql;
    requires java.desktop;
    requires java.annotation;

    requires com.basumatarau.imProject.persistence.lib;
    requires com.basumatarau.imProject.persistence.autoconfigure;
}