module com.basumatarau.imProject.service.userService {

    requires com.basumatarau.imProject.serializer;

    requires spring.beans;
    requires spring.context;
    requires spring.data.commons;

    exports com.basumatarau.imProject.service.userService;
}