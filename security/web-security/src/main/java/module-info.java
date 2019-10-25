module com.basumatarau.imProject.security.webSecurity {
    requires spring.security.core;
    requires spring.security.config;
    requires spring.context;
    requires spring.beans;
    requires spring.web;

    requires com.basumatarau.imProject.service.userDetailsServiceImpl;
    requires javax.servlet.api;
    requires spring.core;

    exports com.basumatarau.imProject.security.webSecurity.config;
}