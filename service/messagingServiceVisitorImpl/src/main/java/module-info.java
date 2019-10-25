import com.basumatarau.imProject.service.messagingServiceVisitor.impl.MessagingServiceVisitorImpl;

module com.basumatarau.imProject.service.messagingServiceVisitorImpl {

    requires spring.context;
    requires spring.beans;

    requires com.basumatarau.imProject.serializer;
    requires com.basumatarau.imProject.service.messagingServiceVisitor;
    requires com.basumatarau.imProject.service.messagingService;

    provides com.basumatarau.imProject.service.messagingServiceVisitor
            with MessagingServiceVisitorImpl;

    exports com.basumatarau.imProject.service.messagingServiceVisitor.impl;
}