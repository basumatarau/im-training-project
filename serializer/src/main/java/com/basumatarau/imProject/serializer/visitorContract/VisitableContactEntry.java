package com.basumatarau.imProject.serializer.visitorContract;

import com.basumatarau.imProject.serializer.customDto.IncomingMessageDto;

public interface VisitableContactEntry<T extends VisitorServiceContract,
                                        R extends IncomingMessageDto>{

    void accept(T visitor, R msg) throws InstantiationException;
}
