package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.PrivateMessage;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.MessageStatusInfoDto;
import com.basumatarau.imProject.serializer.customDto.MessageWithDetailsDto;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PrivateMessageToMessageWithDetailsDto
        implements Converter<PrivateMessage, MessageWithDetailsDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageWithDetailsDto convert(MappingContext<PrivateMessage, MessageWithDetailsDto> context) {
        return context.getSource() == null ? null :
                new MessageWithDetailsDto(
                        context.getSource().getId(),
                        modelMapper.map(context.getSource().getAuthor(), UserProfileDto.class),
                        context.getSource().getBody(),
                        new Date(context.getSource().getTimeSent()),
                        modelMapper.map(context.getSource().getPersonalContact(), ContactEntryVo.class),
                        new MessageStatusInfoDto[]{
                                modelMapper.map(context.getSource().getDelivery(), MessageStatusInfoDto.class)
                        }
                );
    }
}
