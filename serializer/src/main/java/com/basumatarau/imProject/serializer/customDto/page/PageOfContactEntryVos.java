package com.basumatarau.imProject.serializer.customDto.page;

import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.jsonSerializer.PageOfContactEntriesSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonSerialize(using = PageOfContactEntriesSerializer.class)
public class PageOfContactEntryVos extends PageImpl<ContactEntryVo> {

    public PageOfContactEntryVos(List<ContactEntryVo> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
