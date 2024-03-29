package com.basumatarau.imProject.persistenceTest;

import com.basumatarau.imProject.persistence.lib.model.ContactEntry;
import com.basumatarau.imProject.persistence.lib.model.PersonalContact;
import com.basumatarau.imProject.persistence.lib.model.user.User;
import com.basumatarau.imProject.persistence.lib.repository.ContactEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactEntryRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    private User owner;

    @Before
    public void initContactRepoTest() throws InstantiationException {
        owner = users.stream().findAny().orElseThrow(
                () -> new RuntimeException("before test condition has not been met - collection empty"));

        users.remove(owner);
        final Set<PersonalContact> personalContacts = new HashSet<>();
        for (User contactPerson : users) {

            personalContacts.add(new PersonalContact.ContactBuilder()
                    .owner(owner)
                    .person(contactPerson)
                    .confirmed(true)
                    .build()
            );
        }
        owner.getContactEntries().addAll(personalContacts);
        userRepository.save(owner);
    }

    @Test
    public void whenUserHasContact_thenGetContactDetails(){
        final User retrievedUserByEmail = userRepository.findUserWithPersonalContactsByEmail(owner.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));

        assertThat(retrievedUserByEmail.getRole()).isNotNull();

        final Set<ContactEntry> contacts = retrievedUserByEmail.getContactEntries();
        assertThat(contacts.isEmpty()).isFalse();
        contacts.forEach(contact -> {
            assertThat(contact.getOwner().equals(owner)).isTrue();
        });
    }

    @Test
    public void whenUserAddedContacts_thenTheNewContactsPersistedCorrectly()
            throws InstantiationException {
        final User retrieved = userRepository.findByEmail(owner.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        assertThat(retrieved).isEqualTo(owner);
    }

    @Test
    public void whenUserHasContacts_thenContactsCanBeRetrieved()
            throws InstantiationException {

        final PageRequest pageable = PageRequest.of(0, 10);

        final Slice<ContactEntry> contacts =
                contactEntryRepository.getConfirmedContactsForUser(owner, pageable);

        assertThat(contacts.getNumberOfElements()).isPositive();

        for (ContactEntry contactEntry : contacts.getContent()) {
            assertThat(contactEntry.getOwner().equals(owner)).isTrue();
        }
    }

}
