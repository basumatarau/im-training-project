import com.basumatarau.imProject.service.contactEntryServiceImpl.impl.ContactEntryServiceImpl;

module com.basumatarau.imProject.service.contactEntryServiceImpl {

    requires modelmapper;
    requires java.persistence;
    requires spring.context;
    requires spring.tx;
    requires spring.data.commons;
    requires spring.beans;

    requires com.basumatarau.imProject.serializer;
    requires com.basumatarau.imProject.persistence;
    requires com.basumatarau.imProject.service.exception;
    requires com.basumatarau.imProject.service.contactEntryService;
    requires com.basumatarau.imProject.convertor;

    provides com.basumatarau.imProject.service.contactEntryService.ContactService
            with ContactEntryServiceImpl;

    exports com.basumatarau.imProject.service.contactEntryServiceImpl.impl;
}