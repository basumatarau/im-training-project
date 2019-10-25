package com.basumatarau.imProject.serializer.customDto.page;

import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import com.basumatarau.imProject.serializer.jsonSerializer.PageOfUserProfilesSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonSerialize(using = PageOfUserProfilesSerializer.class)
public class PageOfUserProfileDtos extends PageImpl<UserProfileDto> {

    public PageOfUserProfileDtos(List<UserProfileDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
