package com.basumatarau.imProject.service.messagingServiceVisitor.impl;

import com.basumatarau.imProject.service.messagingService.MessagingService;
import com.basumatarau.imProject.service.messagingServiceVisitor.MessageVisitorService;
import com.basumatarau.imProject.serializer.customDto.IncomingMessageDto;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = "com.basumatarau.imProject.service")
public class MessagingServiceVisitorImpl implements MessageVisitorService {

    private final MessagingService messagingService;

    public MessagingServiceVisitorImpl(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public void visit(IncomingMessageDto msg, PersonalContactVo personalContactVo)
            throws InstantiationException {
        messagingService.sendPrivateMessage(msg, personalContactVo);
    }

    @Override
    public void visit(IncomingMessageDto msg, SubscriptionVo subscriptionVo)
            throws InstantiationException {
        messagingService.sendDistributedMessage(msg, subscriptionVo);
    }
}
