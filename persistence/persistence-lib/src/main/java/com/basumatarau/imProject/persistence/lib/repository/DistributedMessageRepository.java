package com.basumatarau.imProject.persistence.lib.repository;

import com.basumatarau.imProject.persistence.lib.model.ChatRoom;
import com.basumatarau.imProject.persistence.lib.model.DistributedMessage;
import com.basumatarau.imProject.persistence.lib.model.Message;
import com.basumatarau.imProject.persistence.lib.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistributedMessageRepository
        extends JpaRepository<DistributedMessage, Long> {

    List<Message> findByChatRoom(ChatRoom chatRoom);

    Slice<Message> findByChatRoom(ChatRoom chatRoom, Pageable pageable);

    @Query(value = "select msg from DistributedMessage msg " +
            "where msg.chatRoom.id=:#{#sub.chatRoom.id} " +
            "and msg.timeSent>:#{#sub.enteredChat} ")
    Page<DistributedMessage> findMessagesForSubscription(
            Pageable pageable,
            @Param("sub") Subscription sub);
}
