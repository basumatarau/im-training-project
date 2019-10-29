package com.basumatarau.imProject.persistence.repository;

import com.basumatarau.imProject.persistence.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    @Query("select u from User u where u.email = ?1 and u.isEnabled = true")
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.contactEntries where u.email=?1 " +
            "and u.isEnabled=true ")
    Optional<User> findUserWithContactEntriesByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (PersonalContact) " +
            "and u.isEnabled=true ")
    Optional<User> findUserWithPersonalContactsByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Subscription) " +
            "and u.isEnabled=true ")
    Optional<User> findUserWithSubscriptionsByEmail(String email);

    @Query("select u from User u where u.isEnabled = true")
    @Override
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
