package com.basumatarau.imProject.service.messagingService.impl;

import com.basumatarau.imProject.service.messagingService.MessagingService;
import com.basumatarau.imProject.persistence.model.*;
import com.basumatarau.imProject.persistence.repository.*;
import com.basumatarau.imProject.serializer.customDto.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
@ComponentScan(basePackages = "com.basumatarau.imProject.persistence")
public class MessagingServiceImpl implements MessagingService {

    private DistributedMessageRepository distributedMessageRepository;

    private PrivateMessageRepository privateMessageRepository;

    private ChatRoomRepository chatRoomRepository;

    private UserRepository userRepository;

    private ContactEntryRepository contactEntryRepository;

    private SimpMessagingTemplate simpMessagingTemplate;

    private ModelMapper modelMapper;

    public MessagingServiceImpl() {
    }

    @Autowired
    public MessagingServiceImpl(DistributedMessageRepository distributedMessageRepository,
                                PrivateMessageRepository privateMessageRepository,
                                ChatRoomRepository chatRoomRepository,
                                SimpMessagingTemplate simpMessagingTemplate,
                                ModelMapper modelMapper,
                                UserRepository userRepository,
                                ContactEntryRepository contactEntryRepository) {

        this.distributedMessageRepository = distributedMessageRepository;
        this.privateMessageRepository = privateMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.contactEntryRepository = contactEntryRepository;
    }

    @Override
    public void sendPrivateMessage(IncomingMessageDto messageDto,
                                   PersonalContactVo personalContactVo) throws InstantiationException {

        final PrivateMessage newPrivateMessage = composePrivateMessage(messageDto, personalContactVo);

        privateMessageRepository.save(newPrivateMessage);

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getOwner().getEmail(),
                "/queue",
                modelMapper.map(newPrivateMessage, MessageWithDetailsDto.class)
        );

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getPerson().getEmail(),
                "/queue",
                modelMapper.map(newPrivateMessage, MessageDto.class)
        );
    }

    private PrivateMessage composePrivateMessage(IncomingMessageDto messageDto,
                                                 PersonalContactVo personalContactVo)
            throws InstantiationException {

        final User author = userRepository.findByEmail(personalContactVo.getOwner().getEmail())
                .orElseThrow(() -> new EntityNotFoundException("no user found"));

        final PersonalContact personalContact = contactEntryRepository.findPersonalContactById(personalContactVo.getId())
                .orElseThrow(() -> new EntityNotFoundException("no contact found"));

        final PrivateMessage newPrivateMessage =
                new PrivateMessage.PrivateMessageBuilder()
                        .author(author)
                        .contact(personalContact)
                        .body(messageDto.getBody())
                        .timeSent(new Date().getTime())
                        .build();

        if(messageDto.getResources() != null) {
            for (MessageResourceDto resource : messageDto.getResources()) {
                MessageResource messageResource = modelMapper.map(resource, MessageResource.class);
                newPrivateMessage.getResources().add(messageResource);
            }
        }
        return newPrivateMessage;
    }

    @Override
    public void sendDistributedMessage(IncomingMessageDto messageDto,
                                       SubscriptionVo subscriptionVo) throws InstantiationException {
        final ChatRoom chatRoom =
                chatRoomRepository
                .findChatRoomByIdWithAllPeers(subscriptionVo.getChatRoom().getId())
                .orElseThrow(() -> new EntityNotFoundException("no chatRoom found"));

        final User author =
                userRepository
                .findByEmail(subscriptionVo.getOwner().getEmail())
                .orElseThrow(() -> new EntityNotFoundException("no user found"));

        final Subscription authorsSubscription =
                contactEntryRepository
                .findSubscriptionByChatRoomAndOwner(chatRoom, author)
                .orElseThrow(() -> new EntityNotFoundException("no sub found"));

        final DistributedMessage newDistributedMessage = composeDistributedMessage(messageDto, chatRoom, author);

        distributedMessageRepository.save(newDistributedMessage);

        for (Subscription targetSub : chatRoom.getSubscriptions()) {
            MessageDto payload = convertToMessageDto(newDistributedMessage, authorsSubscription, targetSub);
            String targetQueueName = targetSub.getOwner().getEmail();

            simpMessagingTemplate.convertAndSendToUser(
                    targetQueueName,
                    "/queue",
                    payload
            );
        }
    }

    private MessageDto convertToMessageDto(DistributedMessage newDistributedMessage,
                                           Subscription authorsSubscription,
                                           Subscription targetSub) {
        MessageDto messageDto;
        if(authorsSubscription.equals(targetSub)){
            messageDto = getMessageWithDetailsDto(newDistributedMessage, targetSub);
        }else {
            messageDto = getMessageDto(newDistributedMessage, authorsSubscription);
        }
        return messageDto;
    }

    private MessageDto getMessageDto(DistributedMessage newDistributedMessage, Subscription authorsSubscription) {
        MessageDto messageDto;
        messageDto = new MessageDto(
                newDistributedMessage.getId(),
                modelMapper.map(newDistributedMessage.getAuthor(), UserProfileDto.class),
                newDistributedMessage.getBody(),
                new Date(newDistributedMessage.getTimeSent()),
                modelMapper.map(authorsSubscription, ContactEntryVo.class)
        );
        return messageDto;
    }

    private MessageDto getMessageWithDetailsDto(DistributedMessage newDistributedMessage, Subscription targetSub) {
        MessageDto messageDto;
        messageDto = new MessageWithDetailsDto(
                newDistributedMessage.getId(),
                modelMapper.map(newDistributedMessage.getAuthor(), UserProfileDto.class),
                newDistributedMessage.getBody(),
                new Date(newDistributedMessage.getTimeSent()),
                modelMapper.map(targetSub, ContactEntryVo.class),
                newDistributedMessage
                        .getDeliveries()
                        .stream()
                        .map(statusInfo -> modelMapper.map(statusInfo, MessageStatusInfoDto.class))
                        .toArray(MessageStatusInfoDto[]::new)
        );
        return messageDto;
    }

    private DistributedMessage composeDistributedMessage(IncomingMessageDto messageDto,
                                                         ChatRoom chatRoom,
                                                         User author)
            throws InstantiationException {
        final DistributedMessage newDistributedMessage =
                new DistributedMessage.DistributedMessageBuilder()
                        .author(author)
                        .chatRoom(chatRoom)
                        .body(messageDto.getBody())
                        .timeSent(new Date().getTime())
                        .build();

        if(messageDto.getResources() != null) {
            for (MessageResourceDto resource : messageDto.getResources()) {
                newDistributedMessage
                        .getResources()
                        .add(modelMapper.map(resource, MessageResource.class));
            }
        }
        return newDistributedMessage;
    }
}
