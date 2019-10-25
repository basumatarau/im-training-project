package com.basumatarau.imProject.service.contactEntryServiceImpl.impl;

import com.basumatarau.imProject.persistence.model.ContactEntry;
import com.basumatarau.imProject.persistence.model.PersonalContact;
import com.basumatarau.imProject.persistence.model.Subscription;
import com.basumatarau.imProject.persistence.model.User;
import com.basumatarau.imProject.persistence.repository.ContactEntryRepository;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import com.basumatarau.imProject.serializer.customDto.ContactEntryVo;
import com.basumatarau.imProject.serializer.customDto.PersonalContactVo;
import com.basumatarau.imProject.serializer.customDto.SubscriptionVo;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import com.basumatarau.imProject.serializer.customDto.page.PageOfContactEntryVos;
import com.basumatarau.imProject.service.contactEntryService.ContactService;
import com.basumatarau.imProject.service.exception.NoEntityFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "com.basumatarau.imProject.persistence")
public class ContactEntryServiceImpl implements ContactService {

    private ContactEntryRepository contactEntryRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public ContactEntryServiceImpl() {
    }

    @Autowired
    public ContactEntryServiceImpl(ContactEntryRepository contactEntryRepository,
                                   UserRepository userRepository,
                                   ModelMapper modelMapper) {
        this.contactEntryRepository = contactEntryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ContactEntryVo getContactEntryForUserByEntryId(Long contactEntryId, Long ownerId) {
        final ContactEntry contactEntry =
                contactEntryRepository.findContactEntryByContactIdAndOwnerId(contactEntryId, ownerId)
                .orElseThrow(() -> new NoEntityFoundException("no contact entry has been found"));

        return modelMapper.map(contactEntry, ContactEntryVo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto userDto, Pageable pageable) {
        final User user = modelMapper.map(userDto, User.class);

        final Page<ContactEntry> allContactsByOwner =
                contactEntryRepository.getConfirmedContactsForUser(user, pageable);

        final List<ContactEntryVo> contacts = allContactsByOwner.getContent()
                .stream()
                .map(c -> modelMapper.map(c, ContactEntryVo.class))
                .collect(Collectors.toList());

        return new PageOfContactEntryVos(contacts, pageable, contacts.size());
    }

    @Override
    public ContactEntryVo findContactEntryById(Long id) {
        return contactEntryRepository
                .findById(id)
                .map(this::toContactEntryVo)
                .orElseThrow(
                        ()-> new NoEntityFoundException("no contact entry found")
                );
    }

    private ContactEntryVo toContactEntryVo(ContactEntry contactEntry) {
        return modelMapper.map(contactEntry, ContactEntryVo.class);
    }

    @Override
    public PersonalContactVo findPersonalContactById(Long id) {
        return contactEntryRepository
                .findPersonalContactById(id)
                .map(this::toPersonalContactVo)
                .orElseThrow(
                        () -> new NoEntityFoundException("no personal contact found")
                );
    }

    private PersonalContactVo toPersonalContactVo(PersonalContact personalContact) {
        return modelMapper.map(personalContact, PersonalContactVo.class);
    }

    @Override
    public SubscriptionVo findSubscriptionById(Long id) {
        return contactEntryRepository
                .findSubscriptionById(id)
                .map(this::toSubscriptionVo)
                .orElseThrow(
                        () -> new NoEntityFoundException("no subscription found")
                );
    }

    private SubscriptionVo toSubscriptionVo(Subscription subscription) {
        return modelMapper.map(subscription, SubscriptionVo.class);
    }

    @Override
    public void removeContactEntryById(Long entryId) {

    }

    @Override
    public void sendContactRequest(UserProfileDto owner, UserProfileDto person) throws InstantiationException {

    }

    @Override
    public void confirmContactRequest(Long personalContactId) throws InstantiationException {

    }

    @Override
    public void declineContactRequest(Long personalContactId) {

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactEntryVo> getPendingContactsForUser(Long userId, Pageable pageable) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NoEntityFoundException("no user found"));

        final Page<ContactEntry> allContactsByOwner =
                contactEntryRepository.getPendingContactsForUser(user, pageable);

        final List<ContactEntryVo> contacts =
                allContactsByOwner.getContent()
                .stream()
                .map(c -> modelMapper.map(c, ContactEntryVo.class))
                .collect(Collectors.toList());

        return new PageOfContactEntryVos(contacts, pageable, contacts.size());
    }

}
