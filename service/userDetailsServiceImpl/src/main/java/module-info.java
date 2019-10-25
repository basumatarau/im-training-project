import com.basumatarau.imProject.service.userDetailsServiceImpl.impl.UserDetailsServiceImpl;

module com.basumatarau.imProject.service.userDetailsServiceImpl {

    requires java.persistence;
    requires modelmapper;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.security.core;

    requires com.basumatarau.imProject.persistence;
    requires com.basumatarau.imProject.service.exception;

    provides org.springframework.security.core.userdetails
            with UserDetailsServiceImpl;

    exports com.basumatarau.imProject.service.userDetailsServiceImpl.impl;
    exports com.basumatarau.imProject.service.userDetailsServiceImpl.config;
}