import com.basumatarau.imProject.service.userServiceImpl.impl.UserServiceImpl;

module com.basumatarau.imProject.service.userServiceImpl {

    requires java.persistence;
    requires modelmapper;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.data.commons;
    requires spring.data.jpa;

    requires com.basumatarau.imProject.persistence;
    requires com.basumatarau.imProject.serializer;
    requires com.basumatarau.imProject.service.userService;
    requires com.basumatarau.imProject.service.exception;
    requires com.basumatarau.imProject.convertor;

    provides com.basumatarau.imProject.service.userService.UserService
            with UserServiceImpl;

    exports com.basumatarau.imProject.service.userServiceImpl.impl;
    exports com.basumatarau.imProject.service.userServiceImpl.config;
}