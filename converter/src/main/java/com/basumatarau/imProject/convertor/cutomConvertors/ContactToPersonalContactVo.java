package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.PersonalContact;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactToPersonalContactVo implements Converter<PersonalContact, ContactEntryVo> {
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonalContactVo convert(MappingContext<PersonalContact, ContactEntryVo> context) {
        return context.getSource() == null ? null :
                new PersonalContactVo(context.getSource().getId(),
                        modelMapper.map(context.getSource().getOwner(), UserProfileDto.class),
                        modelMapper.map(context.getSource().getPerson(), UserProfileDto.class),
                        context.getSource().getIsConfirmed());
    }
}
