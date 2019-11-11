package com.basumatarau.imProject.persistence.model.meta.user;

import com.basumatarau.imProject.persistence.model.ContactEntry;
import com.basumatarau.imProject.persistence.model.user.User;
import com.basumatarau.imProject.persistence.model.user.User.UserRole;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends Identity_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, UserRole> role;
	public static volatile SingularAttribute<User, String> nickName;
	public static volatile SingularAttribute<User, Boolean> accountConfirmed;
	public static volatile SingularAttribute<User, Boolean> credentialsNonExpired;
	public static volatile SingularAttribute<User, Boolean> isEnabled;
	public static volatile SetAttribute<User, ContactEntry> contactEntries;
	public static volatile SingularAttribute<User, Boolean> accountNonExpired;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> passwordHash;
	public static volatile SingularAttribute<User, Boolean> accountNonLocked;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ROLE = "role";
	public static final String NICK_NAME = "nickName";
	public static final String ACCOUNT_CONFIRMED = "accountConfirmed";
	public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String IS_ENABLED = "isEnabled";
	public static final String CONTACT_ENTRIES = "contactEntries";
	public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String EMAIL = "email";
	public static final String PASSWORD_HASH = "passwordHash";
	public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";

}

