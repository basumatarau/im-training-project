module com.basumatarau.imProject.serializer {

    requires java.validation;
    requires jackson.annotations;
    requires spring.boot;
    requires spring.context;
    requires spring.data.commons;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    requires com.basumatarau.imProject.validator;

    exports com.basumatarau.imProject.serializer.customDto;
    exports com.basumatarau.imProject.serializer.jsonSerializer;
    exports com.basumatarau.imProject.serializer.visitorContract;
    exports com.basumatarau.imProject.serializer.customDto.page;
    exports com.basumatarau.imProject.serializer.config;
}