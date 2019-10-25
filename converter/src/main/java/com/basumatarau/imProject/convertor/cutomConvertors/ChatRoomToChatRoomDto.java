package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.ChatRoom;
import com.basumatarau.imProject.serializer.customDto.ChatRoomDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ChatRoomToChatRoomDto implements Converter<ChatRoom, ChatRoomDto> {

    @Override
    public ChatRoomDto convert(MappingContext<ChatRoom, ChatRoomDto> context) {
        return context.getSource() == null ? null :
                new ChatRoomDto(
                        context.getSource().getId(),
                        context.getSource().getName());
    }
}
