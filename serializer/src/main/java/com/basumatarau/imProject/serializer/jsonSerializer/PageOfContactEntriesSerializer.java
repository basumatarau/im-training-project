package com.basumatarau.imProject.serializer.jsonSerializer;

import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.IOException;

@JsonComponent
public class PageOfContactEntriesSerializer
        extends JsonSerializer<Page<ContactEntryVo>> {
    @Override
    public void serialize(Page<ContactEntryVo> page,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("content");
        for (ContactEntryVo contactEntryVo : page.getContent()) {
            jsonGenerator.writeObject(contactEntryVo);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeBooleanField("first", page.isFirst());
        jsonGenerator.writeBooleanField("last", page.isLast());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("numberOfElements", page.getNumberOfElements());
        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("number", page.getNumber());
        Sort sort = page.getSort();
        jsonGenerator.writeArrayFieldStart("sort");
        for (Sort.Order order : sort) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("property", order.getProperty());
            jsonGenerator.writeStringField("direction", order.getDirection().name());
            jsonGenerator.writeBooleanField("ignoreCase", order.isIgnoreCase());
            jsonGenerator.writeStringField("nullHandling", order.getNullHandling().name());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
