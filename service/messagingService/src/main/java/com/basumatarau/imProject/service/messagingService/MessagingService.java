package com.basumatarau.imProject.service.messagingService;

import com.basumatarau.imProject.serializer.customDto.IncomingMessageDto;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;

public interface MessagingService {

    void sendPrivateMessage(IncomingMessageDto messageDto,
                            PersonalContactVo personalContactVo) throws InstantiationException;

    void sendDistributedMessage(IncomingMessageDto messageDto,
                                SubscriptionVo subscriptionVo) throws InstantiationException;
}
