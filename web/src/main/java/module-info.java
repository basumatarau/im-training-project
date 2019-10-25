module com.basumatarau.imProject.web {

    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;

    requires com.basumatarau.imProject.service.messagingService;
    requires com.basumatarau.imProject.service.contactEntryService;
    requires com.basumatarau.imProject.service.userService;
    requires com.basumatarau.imProject.serializer;

    requires com.basumatarau.imProject.service.userServiceImpl;
    requires com.basumatarau.imProject.security.webSecurity;

    exports com.basumatarau.imProject.web.api;
}