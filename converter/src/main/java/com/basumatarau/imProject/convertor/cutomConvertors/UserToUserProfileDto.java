package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserToUserProfileDto implements Converter<User, UserProfileDto> {

    @Override
    public UserProfileDto convert(MappingContext<User, UserProfileDto> context) {
        return context.getSource() == null ? null :
                new UserProfileDto(context.getSource().getId(),
                        context.getSource().getFirstName(),
                        context.getSource().getLastName(),
                        context.getSource().getNickName(),
                        context.getSource().getEmail());
    }
}
