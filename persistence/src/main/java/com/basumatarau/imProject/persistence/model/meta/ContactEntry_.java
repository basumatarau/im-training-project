package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.ContactEntry;
import com.basumatarau.imProject.persistence.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactEntry.class)
public abstract class ContactEntry_ {

	public static volatile SingularAttribute<ContactEntry, User> owner;
	public static volatile SingularAttribute<ContactEntry, Long> id;

	public static final String OWNER = "owner";
	public static final String ID = "id";

}

