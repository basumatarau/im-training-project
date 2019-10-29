package com.basumatarau.imProject.persistenceTest;

import com.basumatarau.imProject.persistence.model.ChatRoom;
import com.basumatarau.imProject.persistence.model.Subscription;
import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.repository.ChatRoomRepository;
import com.basumatarau.imProject.persistence.repository.ContactEntryRepository;
import com.basumatarau.imProject.persistence.repository.UserRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SubscriptionRepositoryTest extends BaseRepositoryTest{
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSubscriptionPersisted_thenTheSubscriptionInstanceFetchedWithUserEntity()
            throws InstantiationException {
        final ChatRoom anyChatRoom = chatRoomRepository
                .findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("failed to fetch any chat room"));
        final User anyUser = users.stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("failed to fetch any user"));

        final Subscription adminSubscription = new Subscription.SubscriptionBuilder()
                .chatRoom(anyChatRoom)
                .owner(anyUser)
                .enabled(true)
                .enteredChat(new Date().getTime())
                .privilege(Subscription.ChatRoomPrivilege.CHATADMIN)
                .build();
        contactEntryRepository.save(adminSubscription);

        AssertionsForClassTypes.assertThat(userRepository
                .findUserWithSubscriptionsByEmail(anyUser.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"))
                .getContactEntries()
                .contains(adminSubscription)).isTrue();
    }
}
