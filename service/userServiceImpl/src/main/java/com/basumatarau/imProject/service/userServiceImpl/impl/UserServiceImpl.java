package com.basumatarau.imProject.service.userServiceImpl.impl;

import com.basumatarau.imProject.persistence.model.*;
import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.ChatRoomRepository;
import com.basumatarau.imProject.persistence.repository.ContactEntryRepository;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import com.basumatarau.imProject.serializer.customDto.*;
import com.basumatarau.imProject.serializer.customDto.page.PageOfUserProfileDtos;
import com.basumatarau.imProject.service.exception.UserAccountOccupied;
import com.basumatarau.imProject.service.userService.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ChatRoomRepository chatRoomRepository;

    private ContactEntryRepository contactEntryRepository;

    private ModelMapper modelMapper;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ChatRoomRepository chatRoomRepository,
                           ContactEntryRepository contactEntryRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.contactEntryRepository = contactEntryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<UserProfileDto> searchForUsers(
            Pageable pageable,
            SearchCriteriaDto criteriaDto) {

        final Page<User> users = userRepository.findAll(
                modelMapper.<Specification<User>>map(
                        criteriaDto,
                        new TypeToken<Specification<User>>() {
                        }.getType()
                ),
                pageable);

        final List<UserProfileDto> content =
                users.getContent()
                        .stream()
                        .map(user -> modelMapper.map(user, UserProfileDto.class))
                        .collect(Collectors.toList());

        return new PageOfUserProfileDtos(content, pageable, users.getTotalElements());
    }

    @Override
    public UserProfileDto getUserById(Long id) {
        final User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        return toUserProfileDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileByUserEmail(String email) {
        final User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("no user found"));

        return toUserProfileDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ContactEntryVo> getUserContactEntriesByUserEmail(String email) {
        final User userWithContactEntries = userRepository
                .findUserWithContactEntriesByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("no user found"));

        return toContactEntryVoSet(userWithContactEntries.getContactEntries());
    }

    @Override
    @Transactional
    public void registerNewUserAccount(UserAccountRegistrationDto accountDetails) {
        if (userRepository.findByEmail(accountDetails.getEmail()).isPresent()) {
            throw new UserAccountOccupied(
                    "account with email: " + accountDetails.getEmail() + " is already occupied");
        }
        final User newUser = toUser(accountDetails);
        userRepository.save(newUser);
    }

    private UserProfileDto toUserProfileDto(User user) {
        return modelMapper.map(user, UserProfileDto.class);
    }

    private Set<ContactEntryVo> toContactEntryVoSet(
            Set<ContactEntry> contactEntries) {
        return modelMapper.map(contactEntries, new TypeToken<Set<ContactEntryVo>>() {
        }.getType());
    }

    @Override
    public void createNewChatRoom(
            UserProfileDto userProfile,
            NewChatRoomDto chatRoomDto) {

        final ChatRoom newChatRoom;
        try {
            newChatRoom = new ChatRoom.ChatRoomBuilder()
                    .isPublic(chatRoomDto.getPub())
                    .name(chatRoomDto.getChatRoomName())
                    .timeCreated(new Date().toInstant().toEpochMilli())
                    .build();

            for (Long invitedUserId : chatRoomDto.getInvitedUserIds()) {
                final PersonalContact personalContact = contactEntryRepository
                        .findContactByOwnerIdAndPersonId(
                                userProfile.getId(),
                                invitedUserId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("no user found with id=" + invitedUserId)
                        );

                final User subscribedUser = userRepository.findById(invitedUserId)
                        .orElseThrow(()-> new EntityNotFoundException("no user found"));

                newChatRoom.getSubscriptions().add(
                        new Subscription.SubscriptionBuilder()
                                .owner(subscribedUser)
                                .privilege(Subscription.ChatRoomPrivilege.COMMONER)
                                .chatRoom(newChatRoom)
                                .enabled(true)
                                .enteredChat(new Date().toInstant().toEpochMilli())
                                .build()
                );
            }
            final User chatCreator = userRepository.findById(userProfile.getId())
                    .orElseThrow(()-> new EntityNotFoundException("no user found"));

            newChatRoom.getSubscriptions().add(
                new Subscription.SubscriptionBuilder()
                        .owner(chatCreator)
                        .privilege(Subscription.ChatRoomPrivilege.CHATADMIN)
                        .chatRoom(newChatRoom)
                        .enabled(true)
                        .enteredChat(new Date().toInstant().toEpochMilli())
                        .build()
            );

        } catch (InstantiationException e) {
            throw new UnsupportedOperationException(e);
        }

        chatRoomRepository.save(newChatRoom);
    }

    private User toUser(UserAccountRegistrationDto accountDto) {
        return modelMapper.map(accountDto, User.class);
    }
}
