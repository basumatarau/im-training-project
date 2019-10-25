package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.StatusInfo;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.MessageStatusInfoDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class MessageStatusInfoToMessageStatusInfoDto
        implements Converter<StatusInfo, MessageStatusInfoDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageStatusInfoDto convert(MappingContext<StatusInfo, MessageStatusInfoDto> context) {
        final Boolean delivered = context.getSource().getDelivered();
        final Boolean read = context.getSource().getRead();

        return context.getSource() == null ? null :
                new MessageStatusInfoDto(
                        context.getSource().getMessage().getId(),
                        delivered,
                        delivered ? new Date(context.getSource().getTimeDelivered()) : null,
                        read,
                        read ? new Date(context.getSource().getTimeRead()) : null,
                        modelMapper.map(context.getSource().getContactEntry(), ContactEntryVo.class)
                );
    }
}
