package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.PrivateMessage;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.MessageDto;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PrivateMessageToMessageDto
        implements Converter<PrivateMessage, MessageDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDto convert(MappingContext<PrivateMessage, MessageDto> context) {
        return context.getSource() == null ? null :
                new MessageDto(
                        context.getSource().getId(),
                        modelMapper.map(context.getSource().getAuthor(), UserProfileDto.class),
                        context.getSource().getBody(),
                        new Date(context.getSource().getTimeSent()),
                        modelMapper.map(context.getSource().getPersonalContact(), ContactEntryVo.class)
                );
    }
}
