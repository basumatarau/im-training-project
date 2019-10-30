module com.basumatarau.imProject.security.webSecurity {
    requires spring.security.core;
    requires spring.security.config;
    requires spring.context;
    requires spring.beans;
    requires spring.web;

    requires com.basumatarau.imProject.service.userDetailsServiceImpl;
    requires javax.servlet.api;
    requires spring.core;
    requires org.slf4j;
    requires spring.security.web;
    requires spring.security.oauth2.client;
    requires spring.security.oauth2.core;
    requires com.basumatarau.imProject.persistence;
    requires spring.tx;
    requires java.persistence;
    requires spring.boot;
    requires modelmapper;

    requires lombok;
    requires jjwt.api;

    exports com.basumatarau.imProject.security.webSecurity.config;
    exports com.basumatarau.imProject.security.webSecurity.oauth2.user;
}