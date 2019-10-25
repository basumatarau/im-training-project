module com.basumatarau.imProject.convertor {

    requires java.desktop;
    requires modelmapper;
    requires spring.context;
    requires spring.beans;
    requires spring.data.jpa;

    requires com.basumatarau.imProject.serializer;
    requires com.basumatarau.imProject.persistence;

    exports com.basumatarau.imProject.convertor.cutomConvertors;
    exports com.basumatarau.imProject.convertor.config;
}