package com.basumatarau.imProject.persistence.model.user;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users", schema = "instant_messenger_db_schema")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Identity {

    public static final String TYPE_PERSON = "bot";
    public static final String TYPE_USER = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "identity_generator")
    @SequenceGenerator(name = "identity_generator",
            sequenceName = "identity_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uuid", nullable = false)
    @EqualsAndHashCode.Exclude
    private ExternalId externalId;

    @Embedded
    @EqualsAndHashCode.Exclude
    @Setter
    private AuthenticationDetails details;

    @Builder
    public Identity(AuthenticationDetails details) {
        this.details = details;
    }

    @PrePersist
    private void initExternalId() {
        if (externalId == null) {
            externalId = new ExternalId();
        }
    }
}

