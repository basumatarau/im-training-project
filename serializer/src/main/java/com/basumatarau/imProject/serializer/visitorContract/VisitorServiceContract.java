package com.basumatarau.imProject.serializer.visitorContract;

import com.basumatarau.imProject.serializer.customDto.IncomingMessageDto;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;

public interface VisitorServiceContract {
    void visit(IncomingMessageDto msg,
               PersonalContactVo personalContactVo) throws InstantiationException;

    void visit(IncomingMessageDto msg,
               SubscriptionVo subscriptionVo) throws InstantiationException;
}
