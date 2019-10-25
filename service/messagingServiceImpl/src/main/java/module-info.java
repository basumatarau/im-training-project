import com.basumatarau.imProject.service.messagingService.impl.MessagingServiceImpl;

module com.basumatarau.imProject.service.messagingServiceImpl {
    requires com.basumatarau.imProject.service.messagingService;
    requires com.basumatarau.imProject.persistence;
    requires com.basumatarau.imProject.serializer;

    requires spring.context;
    requires spring.messaging;
    requires java.persistence;
    requires modelmapper;
    requires spring.beans;

    provides com.basumatarau.imProject.service.messagingService.MessagingService
            with MessagingServiceImpl;

    exports com.basumatarau.imProject.service.messagingService.impl;
}