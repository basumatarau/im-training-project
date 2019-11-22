package com.basumatarau.imProject.persistence.lib.model.user;

import com.basumatarau.imProject.persistence.lib.model.ContactEntry;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = Identity.TYPE_USER)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = "enabled=true")
@SQLDelete(
        sql = "update instant_messenger_db_schema.users set enabled=false where id = ? ",
        check = ResultCheckStyle.COUNT)
public class User extends Identity {

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @EqualsAndHashCode.Exclude
    @Column(name = "first_name")
    private String firstName;

    @EqualsAndHashCode.Exclude
    @Column(name = "last_name")
    private String lastName;

    @EqualsAndHashCode.Exclude
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "email", nullable = false)
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @EqualsAndHashCode.Exclude
    private Boolean accountNonLocked = Boolean.TRUE;

    @EqualsAndHashCode.Exclude
    private Boolean accountNonExpired = Boolean.TRUE;

    @EqualsAndHashCode.Exclude
    private Boolean credentialsNonExpired = Boolean.TRUE;

    private Boolean accountConfirmed = Boolean.TRUE;

    @Column(name = "enabled", nullable = false)
    private Boolean isEnabled;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ContactEntry> contactEntries = new LinkedHashSet<>();

    @Builder(builderMethodName = "userBuilder")
    public User(AuthenticationDetails details,
                UserRole role,
                String firstName,
                String lastName,
                String nickName,
                String email,
                String passwordHash,
                Boolean isEnabled,
                Set<ContactEntry> contactEntries) {

        super(details);

        this.isEnabled = isEnabled;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.contactEntries = contactEntries == null ? this.contactEntries : contactEntries;
    }

    public enum UserRole {
        USER, ADMIN
    }

    @PreRemove
    private void deleteUser() {
        this.isEnabled = false;
    }
}
