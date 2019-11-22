package com.basumatarau.imProject.persistence.lib.repository;

import com.basumatarau.imProject.persistence.lib.model.Message;
import com.basumatarau.imProject.persistence.lib.model.PrivateMessage;
import com.basumatarau.imProject.persistence.lib.model.PersonalContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateMessageRepository
        extends JpaRepository<PrivateMessage, Long> {

    List<Message> findByPersonalContact(PersonalContact personalContact);

    Slice<Message> findByPersonalContact(PersonalContact personalContact, Pageable pageable);
}
