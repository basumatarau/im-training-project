package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.Subscription;
import com.basumatarau.imProject.serializer.customDto.ChatRoomDto;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionToSubscriptionVo implements Converter<Subscription, ContactEntryVo> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubscriptionVo convert(MappingContext<Subscription, ContactEntryVo> context) {
        return context.getSource() == null ? null :
                new SubscriptionVo(context.getSource().getId(),
                modelMapper.map(context.getSource().getOwner(), UserProfileDto.class),
                modelMapper.map(context.getSource().getChatRoom(), ChatRoomDto.class));
    }
}
