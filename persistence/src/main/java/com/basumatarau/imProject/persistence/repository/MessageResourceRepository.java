package com.basumatarau.imProject.persistence.repository;

import com.basumatarau.imProject.persistence.model.ChatRoom;
import com.basumatarau.imProject.persistence.model.ImageResource;
import com.basumatarau.imProject.persistence.model.MessageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageResourceRepository
        extends JpaRepository<MessageResource, Long> {

    @Query(value = "select img from ImageResource img " +
            "")
    List<ImageResource> findImagesForChatRoom(ChatRoom chatRoom);
}
