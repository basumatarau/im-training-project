package com.basumatarau.imProject.serializer.customDto;

import com.basumatarau.imProject.serializer.visitorContract.VisitableContactEntry;
import com.basumatarau.imProject.serializer.visitorContract.VisitorServiceContract;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "contact_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalContactVo.class, name = "personal_contact"),
        @JsonSubTypes.Type(value = SubscriptionVo.class, name = "subscription"),
})
public abstract class ContactEntryVo
        implements VisitableContactEntry<VisitorServiceContract, IncomingMessageDto> {

    private final Long id;
    private final UserProfileDto owner;

    @JsonCreator
    public ContactEntryVo(
            @JsonProperty("id") Long id,
            @JsonProperty("owner") UserProfileDto owner) {
        this.id = id;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public UserProfileDto getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntryVo that = (ContactEntryVo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
