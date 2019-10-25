package com.basumatarau.imProject.service.contactEntryService;

import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto user, Pageable pageable);
    ContactEntryVo findContactEntryById(Long id);
    PersonalContactVo findPersonalContactById(Long id);
    SubscriptionVo findSubscriptionById(Long id);

    void removeContactEntryById(Long entryId);
    void sendContactRequest(UserProfileDto owner, UserProfileDto person) throws InstantiationException;

    //todo security aspect to be implemented
    void confirmContactRequest(Long personalContactId) throws InstantiationException;
    void declineContactRequest(Long personalContactId);

    Page<ContactEntryVo> getPendingContactsForUser(Long userId, Pageable pageable);
    ContactEntryVo getContactEntryForUserByEntryId(Long contactEntryId, Long ownerId);
}
